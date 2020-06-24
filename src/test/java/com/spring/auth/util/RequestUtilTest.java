package com.spring.auth.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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

  @Test
  public void isApiRequestBadClass() {
    ServletRequest request = mock(ServletRequest.class);
    boolean apiRequest = RequestUtil.isApiRequest(request);
    assertEquals(false, apiRequest);
  }
}
