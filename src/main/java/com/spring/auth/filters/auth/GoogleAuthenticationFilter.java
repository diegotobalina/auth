package com.spring.auth.filters.auth;

import com.spring.auth.exceptions.application.*;
import com.spring.auth.filters.auth.parent.AuthenticationFilter;
import com.spring.auth.google.application.ports.GoogleLoginPort;
import com.spring.auth.google.infrastructure.repositories.ports.GoogleGetInfoPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.security.GeneralSecurityException;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
public class GoogleAuthenticationFilter extends AuthenticationFilter {

  private GoogleLoginPort googleLoginPort;

  public GoogleAuthenticationFilter(
      GoogleGetInfoPort googleGetInfoPort, GoogleLoginPort googleLoginPort) {
    this.googleLoginPort = googleLoginPort;
  }

  @Override
  protected User getUserFromToken(String token)
      throws InvalidTokenException, GeneralSecurityException, DuplicatedKeyException,
          LockedUserException, EmailDoesNotExistsException, GoogleGetInfoException,
          NotFoundException, InfiniteLoopException, IOException {
    String tokenWithoutPrefix = TokenUtil.removeGooglePrefix(token);
    return this.googleLoginPort.login(tokenWithoutPrefix);
  }

  @Override
  protected boolean isAcceptedToken(String token) {
    return RegexUtil.isGoogleJwt(token);
  }
}
