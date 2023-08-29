package com.me.authserver.config;

import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;

import com.me.authserver.model.JwtUser;
import com.me.authserver.service.CustomUserDetailsService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class JwtTokenCustomizerConfig {
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(
      CustomUserDetailsService userInfoService) {

    return context -> {
      if (ID_TOKEN.equals(context.getTokenType().getValue())
          || ACCESS_TOKEN.equals(context.getTokenType())) {
        JwtUser userInfo =
            (JwtUser) userInfoService.loadUserByUsername(context.getPrincipal().getName());
        Map<String, Object> info = new HashMap<>();
        info.put("roles", userInfo.getRoles());
        context.getClaims().claims(claims -> claims.putAll(info));
        context.getJwsHeader().type("jwt");
      }
    };
  }
}
