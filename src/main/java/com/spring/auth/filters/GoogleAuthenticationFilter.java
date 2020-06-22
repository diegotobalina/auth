package com.spring.auth.filters;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.util.AuthenticationUtil;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import com.spring.auth.google.application.ports.in.GoogleLoginPort;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import com.spring.auth.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class GoogleAuthenticationFilter extends OncePerRequestFilter {

  private final GoogleGetInfoPort googleGetInfoPort;
  private final GoogleLoginPort googleLoginPort;

  public GoogleAuthenticationFilter(
          GoogleGetInfoPort googleGetInfoPort, GoogleLoginPort googleLoginPort) {
    this.googleGetInfoPort = googleGetInfoPort;
    this.googleLoginPort = googleLoginPort;
  }

  @Override
  protected void doFilterInternal(
      final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
      throws ServletException, IOException {

    // check if should try to process the token
    String jwtWithPrefix = request.getHeader("Authorization");
    boolean isGoogleJwt = RegexUtil.isGoogleJwt(jwtWithPrefix);
    boolean isAuthenticated = AuthenticationUtil.isAuthenticated();
    if (!isGoogleJwt || isAuthenticated) {
      chain.doFilter(request, response);
      return;
    }

    // try to login with the token
    try {
      final String jwtWithoutPrefix = TokenUtil.removeGooglePrefix(jwtWithPrefix);
      final GoogleIdToken.Payload googleInfo = this.googleGetInfoPort.get(jwtWithoutPrefix);
      if (googleInfo == null) throw new Exception("google failed login");
      final User user = this.googleLoginPort.login(googleInfo);
      AuthenticationUtil.authenticate(user);
      log.info("authentication ok for user {}", user.getId());
    } catch (Exception ex) {
      log.warn("exception: {}", ex.getMessage());
    } finally {
      chain.doFilter(request, response);
    }
  }
}
