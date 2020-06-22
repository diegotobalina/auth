package com.spring.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spring.auth.util.CorsUtil;
import com.spring.auth.exceptions.domain.ErrorResponse;
import com.spring.auth.filters.BearerAuthenticationFilter;
import com.spring.auth.filters.GoogleAuthenticationFilter;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import com.spring.auth.google.application.ports.in.GoogleLoginPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${server.auth.secret-key}")
  private String secretKey;

  @Value("${api.prefix}")
  private String apiPrefix;

  private GoogleGetInfoPort googleGetInfoPort;
  private GoogleLoginPort googleLoginPort;

  public WebSecurityConfig(GoogleGetInfoPort googleGetInfoPort, GoogleLoginPort googleLoginPort) {
    this.googleGetInfoPort = googleGetInfoPort;
    this.googleLoginPort = googleLoginPort;
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {

    // config
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // exception handler during the authentication process
    http.exceptionHandling()
        .authenticationEntryPoint(
            (req, rsp, e) -> {
              log.info("UNAUTHORIZED {} {}", req.getMethod(), req.getRequestURI());
              String timestamp = new Timestamp(System.currentTimeMillis()).toString();
              int status = HttpStatus.UNAUTHORIZED.value();
              String error = HttpStatus.UNAUTHORIZED.name();
              String message = e.getMessage();
              String path = req.getRequestURI();
              ErrorResponse errorResponse =
                  new ErrorResponse(timestamp, status, error, message, path);
              ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
              String json = ow.writeValueAsString(errorResponse);
              CorsUtil.setHeaders(rsp);
              rsp.setContentType("application/json");
              rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              rsp.getOutputStream().println(json);
            });

    // custom authentication filters
    final BearerAuthenticationFilter filter = new BearerAuthenticationFilter(secretKey);
    final GoogleAuthenticationFilter googleFilter =
        new GoogleAuthenticationFilter(googleGetInfoPort, googleLoginPort);

    // validate routes
    http.antMatcher("/api/v0/**")
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, apiPrefix + "/oauth2/access")
        .permitAll()
        .antMatchers(HttpMethod.POST, apiPrefix + "/oauth2/login")
        .permitAll()
        .antMatchers(HttpMethod.DELETE, apiPrefix + "/oauth2/logout")
        .permitAll()
        .antMatchers(HttpMethod.GET, apiPrefix + "/oauth2/tokenInfo")
        .permitAll()
        .antMatchers(HttpMethod.POST, apiPrefix + "/users")
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(googleFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
