package com.archyle.fra.friendlyreminderbackend.input;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class TokenGeneratorImpl implements TokenGenerator {

  private final SigningKeyProvider signingKeyProvider;

  @Override
  public String generate(final String username, final EnumSet<Authorities> authorities) {
    validate(username, authorities);
    return Jwts.builder()
        .setSubject(username)
        .claim(
            Claims.AUTHORITIES.name(),
            authorities.stream()
                .map(Authorities::name)
                .reduce("", (s, authority) -> s +"ROLE_" + authority + ";")
                .replaceAll(";$", ""))
        .signWith(signingKeyProvider.get())
        .compact();
  }

  private void validate(String username, EnumSet<Authorities> authorities) {
    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("Invalid username: " + username);
    }
    if (authorities == null || authorities.isEmpty()) {
      throw new IllegalArgumentException("Invalid authorities: " + authorities);
    }
  }
}
