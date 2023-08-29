package com.me.authserver.config;

import com.example.demo.authservice.model.JwtUser;
import com.example.demo.authservice.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

@Configuration
public class JwtTokenCustomizerConfig {
//  @Bean
//  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
//      OidcUserInfoService userInfoService) {
//
//    return context -> {
//      if (ID_TOKEN.equals(context.getTokenType().getValue())
//          || ACCESS_TOKEN.equals(context.getTokenType())) {
//        OidcUserInfo userInfo = userInfoService.loadUser(context.getPrincipal().getName());
//        context.getClaims().claims(claims -> claims.putAll(userInfo.getClaims()));
//        context.getJwsHeader().type("jwt");
//      }
//    };
//  }

  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
          CustomUserDetailsService userInfoService) {

    return context -> {
      if (ID_TOKEN.equals(context.getTokenType().getValue())
              || ACCESS_TOKEN.equals(context.getTokenType())) {
        JwtUser userInfo = (JwtUser) userInfoService.loadUserByUsername(context.getPrincipal().getName());
        Map<String, Object> info = new HashMap<>();
        info.put("roles", userInfo.getRoles());
        context.getClaims().claims(claims -> claims.putAll(info));
        context.getJwsHeader().type("jwt");
      }
    };
  }
}
