package com.spring.auth.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
