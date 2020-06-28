package com.spring.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spring.auth.exceptions.domain.ErrorResponse;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @author diegotobalina created on 24/06/2020 */
public interface RequestUtil {

  static boolean isApiRequest(ServletRequest req) {
    var requestUrl = ((HttpServletRequest) req).getRequestURL();
    String requestUrlString = requestUrl.toString();
    return requestUrlString.contains("/api/");
  }

  static void returnErrorResponse(HttpServletResponse rsp, ErrorResponse errorResponse)
      throws IOException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = ow.writeValueAsString(errorResponse);
    CorsUtil.setHeaders(rsp);
    rsp.setContentType("application/json");
    rsp.setStatus(errorResponse.getStatus());
    rsp.getOutputStream().println(json);
  }
}
