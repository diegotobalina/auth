package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.AuthorizePort;
import com.spring.auth.client.application.ports.out.FindClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.InvalidAuthorizeParamsException;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.TokenUtil;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
public class AuthorizeUseCase implements AuthorizePort {

  FindClientPort findClientPort;
  FindUserPort findUserPort;

  public AuthorizeUseCase(FindClientPort findClientPort, FindUserPort findUserPort) {
    this.findClientPort = findClientPort;
    this.findUserPort = findUserPort;
  }

  @Override
  public String process(
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
      throws NotFoundException, LockedUserException, InvalidAuthorizeParamsException,
          WrongPasswordException {
    Client client = findClientPort.findByClientId(clientId);
    if (!client.getAllowedUrls().contains(originUrl))
      throw new InvalidAuthorizeParamsException("not allowed url: " + originUrl);
    if (!client.getAllowedCallbackUrls().contains(callbackUrl))
      throw new InvalidAuthorizeParamsException("not allowed callback url: " + callbackUrl);
    User user = getUser(username, email, password);
    return getToken(client, user, roleValues, scopeValues);
  }

  private String getToken(
      Client client, User user, List<String> roleValues, List<String> scopeValues) {
    long expirationTime = client.getExpirationTimeToken();
    TokenUtil.JwtWrapper jwtWrapper =
        TokenUtil.generateBearerJwt(
            user, client.getClientSecret(), expirationTime, roleValues, scopeValues);
    return jwtWrapper.getToken(); // no bearer prefix
  }

  private User getUser(String username, String email, String password)
      throws NotFoundException, WrongPasswordException, LockedUserException {
    User user = findUserPort.findByUsernameOrEmail(username, email);
    if (!user.doPasswordsMatch(password)) throw new WrongPasswordException("invalid password");
    // check if the user is locked
    if (user.isLocked()) throw new LockedUserException("this user is locked");
    return user;
  }
}
