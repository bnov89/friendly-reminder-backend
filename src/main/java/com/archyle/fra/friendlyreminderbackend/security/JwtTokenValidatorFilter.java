package com.archyle.fra.friendlyreminderbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

import static com.archyle.fra.friendlyreminderbackend.security.Claims.ACCOUNT_NUMBER;
import static com.archyle.fra.friendlyreminderbackend.security.Claims.AUTHORITIES;

@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private final SigningKeyProvider signingKeyProvider;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader != null) {
      authHeader = authHeader.replace("Bearer ", "");
      Jws<Claims> claims =
          Jwts.parserBuilder()
              .setSigningKey(signingKeyProvider.get())
              .build()
              .parseClaimsJws(authHeader);
      String authorities = claims.getBody().get(AUTHORITIES.name(), String.class);
      String userAccountNumber = claims.getBody().get(ACCOUNT_NUMBER.name(), String.class);
      Authentication authentication =
          new UsernamePasswordAuthenticationToken(
              new Principal(claims.getBody().getSubject(), userAccountNumber),
              null,
              AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
      LOGGER.info("User: {} Authorities: {}", claims.getBody().getSubject(), authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
