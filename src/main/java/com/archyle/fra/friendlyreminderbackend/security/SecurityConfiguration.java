package com.archyle.fra.friendlyreminderbackend.security;

import com.archyle.fra.friendlyreminderbackend.input.SigningKeyProvider;
import com.archyle.fra.friendlyreminderbackend.input.SigningKeyProviderImpl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

  @Value("${fra.security.token.signing-key}")
  private String signingKeyValue;

  @Bean
  public SigningKeyProvider signingKeyProvider() {
    return new SigningKeyProviderImpl(Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKeyValue)));
  }
}
