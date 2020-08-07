package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.*;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface AuthorizePort {
  String process(
      String clientId,
      String originUrl,
      String callbackUrl,
      String resource,
      String responseType,
      String username,
      String email,
      String password,
      List<String> roleValues,
      List<String> scopeValues)
      throws NotFoundException, InvalidTokenException, LockedUserException,
          InvalidAuthorizeParamsException, DuplicatedKeyException, WrongPasswordException;
}
