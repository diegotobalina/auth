package com.spring.auth.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface RequestUtil {

  static boolean isApiRequest(final ServletRequest req) {
    if (!(req instanceof HttpServletRequest)) {
      return false;
    }
    final var requestUrl = ((HttpServletRequest) req).getRequestURL();
    final String requestUrlString = requestUrl.toString();
    return requestUrlString.contains("/api/");
  }
}
