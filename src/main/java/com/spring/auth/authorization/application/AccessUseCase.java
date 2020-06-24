package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.AccessPort;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.application.ports.out.FindSessionByTokenPort;
import com.spring.auth.session.application.ports.out.RefreshSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;

@UseCase
public class AccessUseCase implements AccessPort {

  @Value("${server.auth.secret-key}")
  private String secretKey;

  private FindUserByIdPort findUserByIdPort;
  private FindSessionByTokenPort findSessionByTokenPort;
  private RefreshSessionPort refreshSessionPort;
  private DeleteSessionPort deleteSessionPort;

  public AccessUseCase(
      FindUserByIdPort findUserByIdPort,
      FindSessionByTokenPort findSessionByTokenPort,
      RefreshSessionPort refreshSessionPort,
      DeleteSessionPort deleteSessionPort) {
    this.findUserByIdPort = findUserByIdPort;
    this.findSessionByTokenPort = findSessionByTokenPort;
    this.refreshSessionPort = refreshSessionPort;
    this.deleteSessionPort = deleteSessionPort;
  }

  @Override
  public TokenUtil.JwtWrapper access(String token) throws NotFoundException, InvalidTokenException {
    // findAll session that will ve used for the jwt
    Session session = findSessionByTokenPort.find(token);
    // if is not valid should be removed
    checkValidSession(session);
    // when a session is used should get new expiration time
    refreshSessionPort.refresh(session); // todo: move to event
    // need the user data for the jwt generation
    User user = findUserByIdPort.find(session.getUserId());
    // jwt generation
    return TokenUtil.generateBearerJwt(user, secretKey);
  }

  private void checkValidSession(Session session) throws InvalidTokenException {
    if (session.isValid()) return;

    deleteSessionPort.delete(session);
    throw new InvalidTokenException("token not valid");
  }
}
