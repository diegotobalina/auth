package com.spring.auth.util;

import javax.servlet.http.HttpServletResponse;
/** @author diegotobalina created on 24/06/2020 */
public abstract class CorsUtil {

  public static void setHeaders(final HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
  }
}
