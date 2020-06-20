package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.AccessPort;
import com.spring.auth.authorization.domain.JwtWrapper;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.application.ports.out.FindSessionByTokenPort;
import com.spring.auth.session.application.ports.out.RefreshSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.shared.util.TokenUtil;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.domain.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

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
  public JwtWrapper access(final String token) throws NotFoundException, InvalidTokenException {

    // findAll session that will ve used for the jwt
    final Session session = findSessionByTokenPort.find(token);

    // if is not valid should be removed
    if (!session.isValid()) {
      deleteSessionPort.delete(session);
      throw new InvalidTokenException("token not valid");
    }

    // when a session is used should get new expiration time
    refreshSessionPort.refresh(session);

    // need the user data for the jwt generation
    final User user = findUserByIdPort.find(session.getUserId());

    // jwt generation
    final TokenUtil.JwtWrapper jwtWrapper = TokenUtil.generateBearerJwt(user, secretKey);

    final String jwt = jwtWrapper.getToken();
    final Date issuedAt = jwtWrapper.getIssuedAt();
    final Date expiration = jwtWrapper.getExpiration();
    return new JwtWrapper(jwt, issuedAt, expiration, user.getId());
  }
}
