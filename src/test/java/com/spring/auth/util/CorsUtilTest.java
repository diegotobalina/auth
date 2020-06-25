package com.spring.auth.util;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;
/** @author diegotobalina created on 24/06/2020 */
class CorsUtilTest {

  @Test
  public void setHeaders() {
    HttpServletResponse httpServletResponse = new MockHttpServletResponse();
    CorsUtil.setHeaders(httpServletResponse);
    assertNotNull(httpServletResponse.getHeader("Access-Control-Allow-Origin"));
    assertNotNull(httpServletResponse.getHeader("Access-Control-Allow-Methods"));
    assertNotNull(httpServletResponse.getHeader("Access-Control-Max-Age"));
    assertNotNull(httpServletResponse.getHeader("Access-Control-Allow-Headers"));
  }
}
