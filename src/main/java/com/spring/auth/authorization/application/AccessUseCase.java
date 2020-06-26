package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.AccessPort;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.application.ports.out.FindSessionPort;
import com.spring.auth.session.application.ports.out.RefreshSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Value;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
public class AccessUseCase implements AccessPort {

  @Value("${server.auth.secret-key}")
  private String secretKey;

  private FindUserPort findUserPort;
  private FindSessionPort findSessionPort;
  private RefreshSessionPort refreshSessionPort;
  private DeleteSessionPort deleteSessionPort;

  public AccessUseCase(
      FindUserPort findUserPort,
      FindSessionPort findSessionPort,
      RefreshSessionPort refreshSessionPort,
      DeleteSessionPort deleteSessionPort) {
    this.findUserPort = findUserPort;
    this.findSessionPort = findSessionPort;
    this.refreshSessionPort = refreshSessionPort;
    this.deleteSessionPort = deleteSessionPort;
  }

  @Override
  public TokenUtil.JwtWrapper access(String token) throws NotFoundException, InvalidTokenException {
    // findAll session that will ve used for the jwt
    Session session = findSessionPort.findByToken(token);
    // if is not valid should be removed
    checkValidSession(session);
    // when a session is used should get new expiration time
    refreshSessionPort.refresh(session); // todo: move to event
    // need the user data for the jwt generation
    User user = findUserPort.findById(session.getUserId());
    // jwt generation
    return TokenUtil.generateBearerJwt(user, secretKey);
  }

  private void checkValidSession(Session session) throws InvalidTokenException {
    if (session.isValid()) return;

    deleteSessionPort.delete(session);
    throw new InvalidTokenException("token not valid");
  }
}
