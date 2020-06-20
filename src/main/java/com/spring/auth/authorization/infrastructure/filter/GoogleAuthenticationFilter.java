package com.spring.auth.authorization.infrastructure.filter;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.authorization.util.AuthenticationUtil;
import com.spring.auth.google.application.ports.out.GoogleInfoPort;
import com.spring.auth.google.application.ports.out.GoogleLoginPort;
import com.spring.auth.shared.util.RegexUtil;
import com.spring.auth.shared.util.TokenUtil;
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

  private final GoogleInfoPort googleInfoPort;
  private final GoogleLoginPort googleLoginPort;

  public GoogleAuthenticationFilter(
      GoogleInfoPort googleInfoPort, GoogleLoginPort googleLoginPort) {
    this.googleInfoPort = googleInfoPort;
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
      final GoogleIdToken.Payload googleInfo = this.googleInfoPort.get(jwtWithoutPrefix);
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
