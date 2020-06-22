package com.spring.auth.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface RequestUtil {

  static boolean isApiRequest(ServletRequest req) {
    if (!(req instanceof HttpServletRequest)) {
      return false;
    }
    var requestUrl = ((HttpServletRequest) req).getRequestURL();
    String requestUrlString = requestUrl.toString();
    return requestUrlString.contains("/api/");
  }
}
