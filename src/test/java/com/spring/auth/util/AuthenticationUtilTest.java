package com.spring.auth.util;

import com.spring.auth.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AuthenticationUtilTest {

  @Test
  public void authenticate() {
    SecurityContextHolder.getContext().setAuthentication(null);
    assertEquals(false, AuthenticationUtil.isAuthenticated());
    AuthenticationUtil.authenticate(mock(User.class));
    assertEquals(true, AuthenticationUtil.isAuthenticated());
  }
}
