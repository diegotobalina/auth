package com.spring.auth.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
/** @author diegotobalina created on 24/06/2020 */
class RequestUtilTest {

  @Test
  public void isApiRequest() {
    HttpServletRequest request = new MockHttpServletRequest("GET", "/api/v0/test");
    boolean apiRequest = RequestUtil.isApiRequest(request);
    assertEquals(true, apiRequest);
  }

  @Test
  public void isApiRequestNot() {
    HttpServletRequest request = new MockHttpServletRequest("GET", "/test");
    boolean apiRequest = RequestUtil.isApiRequest(request);
    assertEquals(false, apiRequest);
  }
}
