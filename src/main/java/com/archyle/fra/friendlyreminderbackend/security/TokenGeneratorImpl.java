package com.archyle.fra.friendlyreminderbackend.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenGeneratorImpl implements TokenGenerator {

  private final SigningKeyProvider signingKeyProvider;

  @Override
  public String generate(
      final Principal principal,
      final EnumSet<Authorities> authorities,
      final EnumSet<Products> products) {
    validate(principal, authorities);
    return Jwts.builder()
        .setSubject(principal.getUsername())
        .claim(
            Claims.AUTHORITIES.name(),
            authorities.stream()
                .map(Authorities::name)
                .reduce("", (s, authority) -> s + "ROLE_" + authority + ";")
                .replaceAll(";$", ""))
        .claim(
            Claims.ASSIGNED_PRODUCTS.name(),
            products.stream()
                .map(Products::name)
                .reduce("", (s, s2) -> s + s2 + ";")
                .replaceAll(";$", ""))
        .claim(Claims.ACCOUNT_NUMBER.name(), principal.getUserAccountNumber())
        .signWith(signingKeyProvider.get())
        .compact();
  }

  private void validate(Principal principal, EnumSet<Authorities> authorities) {
    if (Objects.isNull(principal)) {
      throw new IllegalArgumentException("Principal is null");
    }
    if (StringUtils.isBlank(principal.getUsername())) {
      throw new IllegalArgumentException("Invalid username: " + principal.getUsername());
    }
    if (StringUtils.isBlank(principal.getUserAccountNumber())) {
      throw new IllegalArgumentException(
          "Invalid user account number: " + principal.getUserAccountNumber());
    }
    if (authorities == null || authorities.isEmpty()) {
      throw new IllegalArgumentException("Invalid authorities: " + authorities);
    }
  }
}
