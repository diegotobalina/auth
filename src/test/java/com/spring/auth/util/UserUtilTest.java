package com.spring.auth.util;

import com.spring.auth.Instancer;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class UserUtilTest {

  Instancer instancer = new Instancer();
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void getUserFromPrincipal() {
    User user = instancer.user();
    Principal principal = instancer.principal(user);
    User expectedResponse = SerializationUtils.clone(user);
    User response = UserUtil.getUserFromPrincipal(principal);
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void getUserIdFromPrincipal() {
    User principal = randomObjectFiller.createAndFill(User.class);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(principal, null);
    String userIdFromPrincipal =
        UserUtil.getUserIdFromPrincipal(usernamePasswordAuthenticationToken);
    assertEquals(principal.getId(), userIdFromPrincipal);
  }

  @Test
  @SneakyThrows
  public void generateUsername() {
    String email = "testing@gmail.com";
    String generatedUsername1 = UserUtil.generateUsername(email);
    Thread.sleep(1);
    String generatedUsername2 = UserUtil.generateUsername(email);
    assertTrue(generatedUsername1.startsWith("testing"));
    assertTrue(generatedUsername2.startsWith("testing"));
    assertTrue((!generatedUsername1.contains("@")));
    assertTrue((!generatedUsername2.contains("@")));
    assertTrue(!(generatedUsername1.equals(generatedUsername2)));
  }

  @Test
  @SneakyThrows
  public void generateRandomPassword() {
    String password1 = UserUtil.generateRandomPassword();
    Thread.sleep(1);
    String password2 = UserUtil.generateRandomPassword();
    assertTrue(!password1.isBlank());
    assertTrue(!password2.isBlank());
    assertTrue(!(password1.equals(password2)));
  }
}
