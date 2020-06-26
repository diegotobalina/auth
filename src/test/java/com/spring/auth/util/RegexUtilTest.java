package com.spring.auth.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** @author diegotobalina created on 24/06/2020 */
class RegexUtilTest {

  @Test
  public void isBearerJwt() {
    String jwt =
        "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIiwiUk9MRV9CT1NPQ09JTl9HRU5FUkFUT1IiXSwic2NvcGVzIjpbIlJFQURfVVNFUiIsIlVQREFURV9VU0VSIiwiUkVBRCIsIkNSRUFURSIsIlVQREFURSIsIkRFTEVURSIsIkJPU09DT0lOX0FETUlOX0dFTkVSQVRPUiJdLCJleHAiOjE1OTMwMDU1OTUsInVzZXIiOiI1ZWRmYTVkMGZiZGU1YjRjZDk0YjNkMzMiLCJpYXQiOjE1OTMwMDUyOTV9.50TBwkoGksQ_u7KZEQc3cOV8wzlcu3SSYBeFHpu1sygYAE_7ycy6GbXMq_OcfBNnFKn5xSowfXVzBIaodWusgw";
    boolean response = RegexUtil.isBearerJwt(jwt);
      assertTrue(response);
  }

  @Test
  public void isNotBearerJwt() {
    String jwt = "dasdadaasa2141412";
    boolean response = RegexUtil.isBearerJwt(jwt);
      assertFalse(response);
  }

  @Test
  public void isGoogleJwt() {
    String jwt =
        "Google eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiLCJST0xFX0FETUlOIiwiUk9MRV9CT1NPQ09JTl9HRU5FUkFUT1IiXSwic2NvcGVzIjpbIlJFQURfVVNFUiIsIlVQREFURV9VU0VSIiwiUkVBRCIsIkNSRUFURSIsIlVQREFURSIsIkRFTEVURSIsIkJPU09DT0lOX0FETUlOX0dFTkVSQVRPUiJdLCJleHAiOjE1OTMwMDU1OTUsInVzZXIiOiI1ZWRmYTVkMGZiZGU1YjRjZDk0YjNkMzMiLCJpYXQiOjE1OTMwMDUyOTV9.50TBwkoGksQ_u7KZEQc3cOV8wzlcu3SSYBeFHpu1sygYAE_7ycy6GbXMq_OcfBNnFKn5xSowfXVzBIaodWusgw";
    boolean response = RegexUtil.isGoogleJwt(jwt);
      assertTrue(response);
  }

  @Test
  public void isNotGoogleJwt() {
    String jwt = "AD21131312dawaddasdadsa";
    boolean response = RegexUtil.isGoogleJwt(jwt);
      assertFalse(response);
  }

  @Test
  public void isEmail() {
    String email = "user@email.com";
    boolean response = RegexUtil.isEmail(email);
      assertTrue(response);
  }

  @Test
  public void isNotEmail() {
    String email = "@email.com";
    boolean response = RegexUtil.isEmail(email);
      assertFalse(response);
  }

  @Test
  public void isSessionToken() {
    String sessionToken = "Bearer 1593005288693-ee8584a1-9adc-472b-afca-0cf9092ac7d8";
    boolean response = RegexUtil.isSessionToken(sessionToken);
      assertTrue(response);
  }

  @Test
  public void isNotSessionToken() {
    String sessionToken = "Bearer -15asdadadad93005288693-ee8584a1-9adc-472b-afca-0cf9092ac7d8";
    boolean response = RegexUtil.isSessionToken(sessionToken);
      assertFalse(response);
  }

  @Test
  public void isValidUsername() {
    String username = "username321._-a";
    boolean response = RegexUtil.isValidUsername(username);
      assertTrue(response);
  }

  @Test
  public void isNotValidUsername() {
    String username = "username321._-a&&&";
    boolean response = RegexUtil.isValidUsername(username);
      assertFalse(response);
  }

  @Test
  public void isValidPassword() {
    String password = "P@ssw1!%d";
    boolean response = RegexUtil.isValidPassword(password);
      assertTrue(response);
  }

  @Test
  public void isNotValidPasswordMinSize() {
    String password = "P@as";
    boolean response = RegexUtil.isValidPassword(password);
      assertFalse(response);
  }

  @Test
  public void isNotValidPasswordMaxSize() {
    String password =
        "VgrYhkmRDJqAWXI8ADemPV6QtNI7qTkRvRZry3FTjVT3GPx3sHf6q6xskM1o06i0Fk7tINlC1aL0yXnVHZLogp7eCuR3HyAG2Cy1rTe751EDgNAJMRPVjniediwYBFe2UHANdSkn5A3hcHaIAWq8plII7Lj73Oe9GQTG5efZO8TBKQDDUi7LUhTlbaUpSKiPshJ9GVRyMQoViL6UwlfwVMfHvx0oqJSTbIETR2vve9z0j3zBbD3nK2z1EykpRYI";
    boolean response = RegexUtil.isValidPassword(password);
      assertFalse(response);
  }
}
