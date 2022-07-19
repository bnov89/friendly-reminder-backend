package com.archyle.fra.friendlyreminderbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

  private final SigningKeyProvider signingKeyProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
            .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/user/login", "/user/register")
        .anonymous()
        .and()
        .addFilterBefore(
            new JwtTokenValidatorFilter(signingKeyProvider), AnonymousAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers("/**")
        .hasRole("ADMINISTRATOR");
  }
}
