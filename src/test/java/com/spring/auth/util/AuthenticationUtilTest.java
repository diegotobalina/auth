package com.spring.auth.util;

import com.spring.auth.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
/** @author diegotobalina created on 24/06/2020 */
class AuthenticationUtilTest {

  @Test
  public void authenticate() {
    SecurityContextHolder.getContext().setAuthentication(null);
    assertFalse(AuthenticationUtil.isAuthenticated());
    AuthenticationUtil.authenticate(mock(User.class));
    assertTrue(AuthenticationUtil.isAuthenticated());
  }
}
