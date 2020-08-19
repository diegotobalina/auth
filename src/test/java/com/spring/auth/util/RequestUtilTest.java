package com.spring.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spring.auth.exceptions.domain.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/** @author diegotobalina created on 24/06/2020 */
class RequestUtilTest {

  @Test
  public void isApiRequest() {
    HttpServletRequest request = new MockHttpServletRequest("GET", "/api/v1/test");
    boolean apiRequest = RequestUtil.isApiRequest(request);
    assertTrue(apiRequest);
  }

  @Test
  public void isApiRequestNot() {
    HttpServletRequest request = new MockHttpServletRequest("GET", "/test");
    boolean apiRequest = RequestUtil.isApiRequest(request);
    assertFalse(apiRequest);
  }

  @Test
  void returnErrorResponse() throws IOException {
    String timestamp = new Date().toString();
    int status = 200;
    String error = "example_error";
    String message = "example_message";
    String path = "example_path";

    ErrorResponse errorResponse = new ErrorResponse(timestamp, status, error, message, path);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(errorResponse);

    HttpServletResponse httpServletResponse = new MockHttpServletResponse();
    RequestUtil.returnErrorResponse(httpServletResponse, errorResponse);

    String responseContentType = httpServletResponse.getContentType();
    int responseStatus = httpServletResponse.getStatus();
    assertEquals("application/json", responseContentType);
    assertEquals(status, responseStatus);
  }
}
