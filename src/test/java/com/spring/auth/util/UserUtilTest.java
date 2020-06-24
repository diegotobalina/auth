package com.spring.auth.util;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserUtilTest {

  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void getUserFromPrincipal() {
    User principal = randomObjectFiller.createAndFill(User.class);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(principal, null);
    User userFromPrincipal = UserUtil.getUserFromPrincipal(usernamePasswordAuthenticationToken);
    assertEquals(principal.toString(), userFromPrincipal.toString());
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
