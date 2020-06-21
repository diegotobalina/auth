package com.spring.auth.authorization.infrastructure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Profile("!dev")
@Component
public class ApiKeyFilter extends OncePerRequestFilter {

  @Value("${api.key}")
  public String apiKey;

  private ObjectMapper mapper;

  public ApiKeyFilter(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
      throws ServletException, IOException {
    String requestApiKey = request.getHeader("api-key");
    if (StringUtils.isBlank(requestApiKey) || !requestApiKey.equals(apiKey)) {
      Map<String, Object> errorDetails = new HashMap<>();
      errorDetails.put("message", "missing or invalid api-key header");
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      mapper.writeValue(response.getWriter(), errorDetails);
    } else {
      chain.doFilter(request, response);
    }
  }
}
