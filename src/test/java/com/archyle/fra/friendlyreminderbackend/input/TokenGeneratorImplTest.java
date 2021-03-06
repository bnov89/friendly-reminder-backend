package com.archyle.fra.friendlyreminderbackend.input;

import com.archyle.fra.friendlyreminderbackend.security.*;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.EnumSet;

import static com.archyle.fra.friendlyreminderbackend.security.Authorities.ADMINISTRATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenGeneratorImplTest {

  public static final String USERNAME = "username@fakeemail.com";
  public static final String USER_ACCOUNT_NUMBER = "123456";
  public static final EnumSet<Authorities> AUTHORITIES = EnumSet.of(ADMINISTRATOR);
  public static final EnumSet<Products> PRODUCTS = EnumSet.of(Products.TODO, Products.MATCH_BET);
  @Mock private Key key;
  @Mock private SigningKeyProvider signingKeyProvider;
  private TokenGenerator ut;
  private String secretKey = "h9oMlwtqdZssnhCWLLzXuXbmLkTxF++tD1qz6dVawbw=";

  @BeforeEach
  void setup() {
    ut = new TokenGeneratorImpl(signingKeyProvider);
  }

  @Test
  void shouldGenerateTokenForGivenUserAndAuthoritiesList() {
    // GIVEN
    when(signingKeyProvider.get())
        .thenReturn(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    // WHEN
    String generatedToken = ut.generate(new Principal(USERNAME, USER_ACCOUNT_NUMBER), AUTHORITIES, PRODUCTS);
    // THEN
    var claimsJws =
        Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(generatedToken).getBody();

    assertThat(claimsJws.getSubject()).isEqualTo(USERNAME);
    assertThat(claimsJws.get(Claims.ACCOUNT_NUMBER.name(), String.class)).isEqualTo(USER_ACCOUNT_NUMBER);
    assertThat(claimsJws.get(Claims.AUTHORITIES.name(), String.class))
        .isEqualTo("ROLE_ADMINISTRATOR");
    assertThat(claimsJws.get(Claims.ASSIGNED_PRODUCTS.name(), String.class))
        .isEqualTo("TODO;MATCH_BET");

  }

  @Test
  void shouldGenerateTokenForGivenUserAndOneAuthority() {
    when(signingKeyProvider.get())
        .thenReturn(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    String generatedToken = ut.generate(new Principal(USERNAME, USER_ACCOUNT_NUMBER), EnumSet.of(ADMINISTRATOR), PRODUCTS);
    var claimsJws =
        Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(generatedToken);
    assertThat(claimsJws.getBody().getSubject()).isEqualTo(USERNAME);
    assertThat(claimsJws.getBody().get(Claims.AUTHORITIES.name(), String.class))
        .isEqualTo("ROLE_REGULAR_USER");
  }

  @Test
  void noAuthorities_shouldThrowException() {
    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> ut.generate(new Principal(USERNAME, USER_ACCOUNT_NUMBER), null, PRODUCTS));
    assertThat(exception).hasMessage("Invalid authorities: null");
  }

  @Test
  void noUsername_shouldThrowException() {
    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> ut.generate(null, AUTHORITIES, PRODUCTS));
    assertThat(exception).hasMessage("Invalid username: null");
  }

  @Test
  void emptyUsername_shouldThrowException() {
    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class, () -> ut.generate(new Principal("", USER_ACCOUNT_NUMBER), AUTHORITIES, PRODUCTS));
    assertThat(exception).hasMessage("Invalid username: ");
  }
}
