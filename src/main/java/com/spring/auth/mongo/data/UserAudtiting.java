package com.spring.auth.mongo.data;

import com.spring.auth.user.domain.User;
import com.spring.auth.util.AuthenticationUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAudtiting implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    if (!AuthenticationUtil.isAuthenticated()) return Optional.of("anonymousUser");
    User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return Optional.of(principal.getId());
  }
}
