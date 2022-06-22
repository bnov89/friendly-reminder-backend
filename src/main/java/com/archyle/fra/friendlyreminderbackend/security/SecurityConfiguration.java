package com.archyle.fra.friendlyreminderbackend.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration {

  @Value("${fra.security.token.signing-key}")
  private String signingKeyValue;

  @Bean
  public SigningKeyProvider signingKeyProvider() {
    return new SigningKeyProviderImpl(Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKeyValue)));
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//                .allowedHeaders("Authorization")
//                .allowCredentials(true);
      }
    };
  }
}
