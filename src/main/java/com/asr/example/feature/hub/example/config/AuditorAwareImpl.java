package com.asr.example.feature.hub.example.config;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public @NotNull Optional<String> getCurrentAuditor() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info(String.valueOf(principal.getClass()));
    return Optional
        .of(principal)
        .filter(Jwt.class::isInstance)
        .map(obj -> ((Jwt) obj).getClaim("email").toString());
  }
}