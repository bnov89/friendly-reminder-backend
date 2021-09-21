package com.archyle.fra.friendlyreminderbackend.input;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.EnumSet;

import static com.archyle.fra.friendlyreminderbackend.input.Authorities.ADMINISTRATOR;
import static com.archyle.fra.friendlyreminderbackend.input.Authorities.REGULAR_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenGeneratorImplTest {

  public static final String USERNAME = "username@fakeemail.com";
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
    when(signingKeyProvider.get())
            .thenReturn(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    String generatedToken = ut.generate(USERNAME, EnumSet.of(ADMINISTRATOR, REGULAR_USER));
    var claimsJws =
        Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(generatedToken);
    assertThat(claimsJws.getBody().getSubject()).isEqualTo(USERNAME);
    assertThat(claimsJws.getBody().get(Claims.AUTHORITIES.name(), String.class))
        .isEqualTo("ADMINISTRATOR;REGULAR_USER");
  }

  @Test
  void shouldGenerateTokenForGivenUserAndOneAuthority() {
    when(signingKeyProvider.get())
            .thenReturn(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    String generatedToken = ut.generate(USERNAME, EnumSet.of(REGULAR_USER));
    var claimsJws =
        Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(generatedToken);
    assertThat(claimsJws.getBody().getSubject()).isEqualTo(USERNAME);
    assertThat(claimsJws.getBody().get(Claims.AUTHORITIES.name(), String.class))
        .isEqualTo("REGULAR_USER");
  }

  @Test
  void noAuthorities_shouldThrowException() {
    IllegalArgumentException exception =
        Assertions.assertThrows(IllegalArgumentException.class, () -> ut.generate(USERNAME, null));
    assertThat(exception).hasMessage("Invalid authorities: null");
  }

  @Test
  void noUsername_shouldThrowException() {
    EnumSet<Authorities> authorities = EnumSet.of(ADMINISTRATOR, REGULAR_USER);
    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> ut.generate(null, authorities));
    assertThat(exception).hasMessage("Invalid username: null");
  }

  @Test
  void emptyUsername_shouldThrowException() {
    EnumSet<Authorities> authorities = EnumSet.of(ADMINISTRATOR, REGULAR_USER);
    IllegalArgumentException exception =
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> ut.generate("", authorities));
    assertThat(exception).hasMessage("Invalid username: ");
  }
}
