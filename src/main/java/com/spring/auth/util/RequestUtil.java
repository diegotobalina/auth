package com.spring.auth.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
/** @author diegotobalina created on 24/06/2020 */
public interface RequestUtil {

  static boolean isApiRequest(ServletRequest req) {
    var requestUrl = ((HttpServletRequest) req).getRequestURL();
    String requestUrlString = requestUrl.toString();
    return requestUrlString.contains("/api/");
  }
}
