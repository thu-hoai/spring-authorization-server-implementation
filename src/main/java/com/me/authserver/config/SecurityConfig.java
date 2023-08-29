package com.me.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Bean
  SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/webjars/**", "/login")
                    .permitAll()
                    .requestMatchers("/oauth2/jwks")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(Customizer.withDefaults());
    return http.build();
  }
}
