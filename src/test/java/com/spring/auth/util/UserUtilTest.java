package com.spring.auth.util;

import com.spring.auth.Instancer;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** @author diegotobalina created on 24/06/2020 */
class UserUtilTest {
  Instancer instancer = new Instancer();

  @Test
  public void getUserFromPrincipal() {
    Principal principal = instancer.principal(instancer.user());
    User expectedResponse = instancer.user();
    User response = UserUtil.getUserFromPrincipal(principal);
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void getUserIdFromPrincipal() {
    var user = instancer.user();
    var principal = instancer.principal(user);
    String userIdFromPrincipal = UserUtil.getUserIdFromPrincipal(principal);
    assertEquals("id", userIdFromPrincipal);
  }

  @Test
  @SneakyThrows
  public void generateUsername() {
    String email = "user@user.com";
    String generateUsername = UserUtil.generateUsername(email);
    String regex = "user[0-9]{4}";
    assertTrue(generateUsername.matches(regex));
  }

  @Test
  @SneakyThrows
  public void generateRandomPassword() {
    String password = UserUtil.generateRandomPassword();
    String regex = "[0-9]{13}-[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}";
    assertTrue(password.matches(regex));
  }
}
