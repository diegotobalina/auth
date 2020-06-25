package com.spring.auth.util;

import com.spring.auth.Instancer;
import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** @author diegotobalina created on 24/06/2020 */
class TokenUtilTest {

  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();
  Instancer instancer = new Instancer();

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
    String secretKey = "secret_key";
    TokenUtil.JwtWrapper jwtWrapper = jwtWrapper(secretKey);

    Assert.assertNotNull(jwtWrapper);
    Assert.assertEquals("id", jwtWrapper.getUserId());

    long issuedAtTime = jwtWrapper.getIssuedAt().getTime();
    long expectedExpirationTime = issuedAtTime + (1000 * 60 * 5);
    Assert.assertEquals(expectedExpirationTime, jwtWrapper.getExpiration().getTime());
    Assert.assertEquals("[value, value]", jwtWrapper.getRoles().toString());
    Assert.assertEquals("[value, value]", jwtWrapper.getScopes().toString());
  }

  @Test
  @SneakyThrows
  public void getValues() {
    String secretKey = "secret_key";
    TokenUtil.JwtWrapper jwtWrapper = jwtWrapper(secretKey);
    TokenUtil.JwtWrapper values = TokenUtil.getValues(jwtWrapper.getToken(), secretKey);

    assertEquals("id", values.getUserId());
    assertEquals(jwtWrapper.getToken(), values.getToken());
    assertEquals(jwtWrapper.getIssuedAt().toString(), values.getIssuedAt().toString());
    assertEquals(jwtWrapper.getExpiration().toString(), values.getExpiration().toString());
    Assert.assertEquals("[value, value]", values.getRoles().toString());
    Assert.assertEquals("[value, value]", values.getScopes().toString());
  }

  @SneakyThrows
  private TokenUtil.JwtWrapper jwtWrapper(String secretKey) {
    List<Role> roles = Arrays.asList(instancer.role(), instancer.role());
    List<Scope> scopes = Arrays.asList(instancer.scope(), instancer.scope());
    User user = instancer.user();
    objectFiller.replace(user, "roles", roles);
    objectFiller.replace(user, "scopes", scopes);
    return TokenUtil.generateBearerJwt(user, secretKey);
  }

  @Test
  public void getValuesException() {
    assertThrows(InvalidTokenException.class, () -> TokenUtil.getValues("", ""));
  }
}
