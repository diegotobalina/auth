package com.spring.auth.filters;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.spring.auth.exceptions.application.*;
import com.spring.auth.google.application.ports.in.GoogleLoginPort;
import com.spring.auth.google.application.ports.out.GoogleGetInfoPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.AuthenticationUtil;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
public class GoogleAuthenticationFilter extends OncePerRequestFilter {

  private GoogleGetInfoPort googleGetInfoPort;
  private GoogleLoginPort googleLoginPort;

  public GoogleAuthenticationFilter(
      GoogleGetInfoPort googleGetInfoPort, GoogleLoginPort googleLoginPort) {
    this.googleGetInfoPort = googleGetInfoPort;
    this.googleLoginPort = googleLoginPort;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
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
      String jwtWithoutPrefix = TokenUtil.removeGooglePrefix(jwtWithPrefix);
      User user = getUserFromGoogleJwt(jwtWithoutPrefix); // get full user from database
      AuthenticationUtil.authenticate(user);
      log.info("authentication ok for user {}", user.getId());
    } catch (Exception ex) {
      log.warn("exception: {}", ex.getMessage());
    } finally {
      chain.doFilter(request, response);
    }
  }

  private User getUserFromGoogleJwt(String jwtWithoutPrefix)
      throws GeneralSecurityException, IOException, GoogleGetInfoException, InfiniteLoopException,
          DuplicatedKeyException, NotFoundException, LockedUserException,
          EmailDoesNotExistsException {
    GoogleIdToken.Payload googleInfo = this.googleGetInfoPort.get(jwtWithoutPrefix);
    return this.googleLoginPort.login(googleInfo);
  }
}
