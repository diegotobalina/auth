package com.spring.auth.util;

import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TokenUtilTest {

  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

  @Test
  public void addBearerPrefix() {
    String token = "example_token";
    String tokenWithBearerPrefix = TokenUtil.addBearerPrefix(token);
    assertEquals("Bearer example_token", tokenWithBearerPrefix);
  }

  @Test
  public void addBearerPrefixWhenHavePrefix() {
    String token = "Bearer example_token";
    String tokenWithBearerPrefix = TokenUtil.addBearerPrefix(token);
    assertEquals("Bearer example_token", tokenWithBearerPrefix);
  }

  @Test
  public void addGooglePrefix() {
    String token = "example_token";
    String tokenWithGooglePrefix = TokenUtil.addGooglePrefix(token);
    assertEquals("Google example_token", tokenWithGooglePrefix);
  }

  @Test
  public void addGooglePrefixWhenHavePrefix() {
    String token = "Google example_token";
    String tokenWithGooglePrefix = TokenUtil.addGooglePrefix(token);
    assertEquals("Google example_token", tokenWithGooglePrefix);
  }

  @Test
  public void removeBearerPrefix() {
    String token = "Bearer example_token";
    String removeBearerPrefix = TokenUtil.removeBearerPrefix(token);
    assertEquals("example_token", removeBearerPrefix);
  }

  @Test
  public void removeBearerPrefixWhenHaveNotPrefix() {
    String token = "example_token";
    String removeBearerPrefix = TokenUtil.removeBearerPrefix(token);
    assertEquals("example_token", removeBearerPrefix);
  }

  @Test
  public void removeGooglePrefix() {
    String token = "Google example_token";
    String removeGooglePrefix = TokenUtil.removeGooglePrefix(token);
    assertEquals("example_token", removeGooglePrefix);
  }

  @Test
  public void removeGooglePrefixWhenHaveNotPrefix() {
    String token = "example_token";
    String removeGooglePrefix = TokenUtil.removeGooglePrefix(token);
    assertEquals("example_token", removeGooglePrefix);
  }

  @Test
  @SneakyThrows
  public void generateJwt() {
    Role role1 = randomObjectFiller.createAndFill(Role.class);
    Role role2 = randomObjectFiller.createAndFill(Role.class);
    List<Role> roles = Arrays.asList(role1, role2);
    Scope scope1 = randomObjectFiller.createAndFill(Scope.class);
    List<Scope> scopes = Arrays.asList(scope1);
    User user = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user, "roles", roles);
    objectFiller.replace(user, "scopes", scopes);
    String secretKey = "placeHolderSecretKey";
    TokenUtil.JwtWrapper jwtWrapper = TokenUtil.generateBearerJwt(user, secretKey);

    Assert.assertNotNull(jwtWrapper);
    Assert.assertEquals(user.getId(), jwtWrapper.getUserId());
    Assert.assertEquals(
        jwtWrapper.getIssuedAt().getTime() + (1000 * 60 * 5), jwtWrapper.getExpiration().getTime());
    Assert.assertEquals(
        String.format("[%s, %s]", role1.getValue(), role2.getValue()),
        jwtWrapper.getRoles().toString());
    Assert.assertEquals(
        String.format("[%s]", scope1.getValue()), jwtWrapper.getScopes().toString());
  }

  @Test
  @SneakyThrows
  public void getValues() throws InvalidTokenException {
    Role role1 = randomObjectFiller.createAndFill(Role.class);
    Role role2 = randomObjectFiller.createAndFill(Role.class);
    List<Role> roles = Arrays.asList(role1, role2);
    Scope scope1 = randomObjectFiller.createAndFill(Scope.class);
    List<Scope> scopes = Arrays.asList(scope1);
    User user = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user, "roles", roles);
    objectFiller.replace(user, "scopes", scopes);
    String secretKey = "placeHolderSecretKey";
    TokenUtil.JwtWrapper jwtWrapper = TokenUtil.generateBearerJwt(user, secretKey);
    TokenUtil.JwtWrapper values = TokenUtil.getValues(jwtWrapper.getToken(), secretKey);

    assertEquals(jwtWrapper.getUserId(), values.getUserId());
    assertEquals(jwtWrapper.getToken(), values.getToken());
    assertEquals(jwtWrapper.getIssuedAt().toString(), values.getIssuedAt().toString());
    assertEquals(jwtWrapper.getExpiration().toString(), values.getExpiration().toString());
    Assert.assertEquals(
        String.format("[%s, %s]", role1.getValue(), role2.getValue()),
        values.getRoles().toString());
    Assert.assertEquals(String.format("[%s]", scope1.getValue()), values.getScopes().toString());
  }

  @Test
  public void getValuesException() {
    assertThrows(InvalidTokenException.class, () -> TokenUtil.getValues("", ""));
  }
}
