package com.spring.auth.util;

import com.spring.auth.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class AuthenticationUtilTest {

  @Test
  public void authenticate() {
    SecurityContextHolder.getContext().setAuthentication(null);
    assertEquals(false, AuthenticationUtil.isAuthenticated());
    AuthenticationUtil.authenticate(mock(User.class));
    assertEquals(true, AuthenticationUtil.isAuthenticated());
  }
}
