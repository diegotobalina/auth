package com.spring.auth.filters.auth;

import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.filters.auth.parent.AuthenticationFilter;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.RegexUtil;
import com.spring.auth.util.TokenUtil;
import com.spring.auth.util.UserUtil;
import lombok.extern.slf4j.Slf4j;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
public class BearerAuthenticationFilter extends AuthenticationFilter {

  private String secretKey;

  public BearerAuthenticationFilter(String secretKey) {
    this.secretKey = secretKey;
  }

  @Override
  protected User getUserFromToken(String token) throws InvalidTokenException {
    String tokenWithoutPrefix = TokenUtil.removeBearerPrefix(token);
    return UserUtil.getUserFromBearerJwt(tokenWithoutPrefix, secretKey);
  }

  @Override
  protected boolean isAcceptedToken(String token) {
    return RegexUtil.isBearerJwt(token);
  }
}
