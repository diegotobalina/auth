package com.spring.auth.logs.infrastructure;

import com.spring.auth.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.util.*;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@Component
public class HttpLoggingFilter implements Filter {

  @Value("${logging.include}")
  List<String> loggingIncludes;

  @Override
  public void init(FilterConfig filterConfig) {}

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
    try {
      HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      BufferedRequestWrapper bufferedRequest = new BufferedRequestWrapper(httpServletRequest);
      BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);

      // log request
      if (isLoggableUri(httpServletRequest) && loggingIncludes.contains("request"))
        logRequest(bufferedRequest);

      long requestMillisStart = System.currentTimeMillis();
      chain.doFilter(bufferedRequest, bufferedResponse);
      long responseMillisStart = System.currentTimeMillis();
      long requestTime = responseMillisStart - requestMillisStart;

      // log response
      if (isLoggableUri(httpServletRequest) && loggingIncludes.contains("response"))
        logResponse(bufferedResponse, requestTime);

    } catch (Throwable a) {
      log.error(a.getMessage());
    }
  }

  private void logRequest(BufferedRequestWrapper bufferedRequest) throws IOException {
    String requestMethod = StringUtil.removeRowJumps(bufferedRequest.getMethod());
    String requestUri = StringUtil.removeRowJumps(bufferedRequest.getRequestURI());
    Map<String, String> requestMap = this.getTypesafeRequestMap(bufferedRequest);
    String requestParams = StringUtil.removeRowJumps(requestMap.toString());
    String requestBody = StringUtil.removeRowJumps(bufferedRequest.getRequestBody());
    String requestHeaders = StringUtil.removeRowJumps(getHeaders(bufferedRequest).toString());
    new Thread(
            () -> {
              log.info("Request method: {}", requestMethod);
              log.info("Request url: {}", requestUri);
              log.info("Request params: {}", requestParams);
              log.info("Request body: {}", requestBody);
              log.info("Request headers: {}", requestHeaders);
            })
        .start();
  }

  private void logResponse(BufferedResponseWrapper bufferedResponse, long requestTime) {
    int responseStatus = bufferedResponse.getStatus();
    String responseHeaders = StringUtil.removeRowJumps(getHeaders(bufferedResponse).toString());
    String responseBody = bufferedResponse.getContent();
    new Thread(
            () -> {
              log.info("Response Code: {}", responseStatus);
              log.info("Response Headers: {}", responseHeaders);
              log.info("Response Body Text: {}", StringUtil.removeRowJumps(responseBody));
              log.info("Total Request Time: {} ms", requestTime);
            })
        .start();
  }

  private boolean isLoggableUri(final HttpServletRequest httpServletRequest) {
    return httpServletRequest.getRequestURL().toString().contains("/api/")
        && !httpServletRequest.getMethod().equals(HttpMethod.OPTIONS);
  }

  private List<String> getHeaders(HttpServletRequest httpServletRequest) {
    Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
    List<String> headers = new ArrayList<>();
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        String headerValue = httpServletRequest.getHeader(headerName);
        headers.add(String.format("%s : %s", headerName, headerValue));
      }
    }
    return headers;
  }

  private List<String> getHeaders(HttpServletResponse httpServletResponse) {
    Collection<String> headerNames = httpServletResponse.getHeaderNames();
    List<String> headers = new ArrayList<>();
    for (String headerName : headerNames) {
      String headerValue = httpServletResponse.getHeader(headerName);
      headers.add(String.format("%s : %s", headerName, headerValue));
    }
    return headers;
  }

  private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
    Map<String, String> typesafeRequestMap = new HashMap<>();
    Enumeration<?> requestParamNames = request.getParameterNames();
    while (requestParamNames.hasMoreElements()) {
      String requestParamName = (String) requestParamNames.nextElement();
      String requestParamValue;
      if (requestParamName.equalsIgnoreCase("password")) {
        requestParamValue = "********";
      } else {
        requestParamValue = request.getParameter(requestParamName);
      }
      typesafeRequestMap.put(requestParamName, requestParamValue);
    }
    return typesafeRequestMap;
  }

  @Override
  public void destroy() {}
}
