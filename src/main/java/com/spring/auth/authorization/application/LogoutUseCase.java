package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.LogoutUserPort;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.application.ports.out.FindSessionPort;
import com.spring.auth.session.domain.Session;
import lombok.AllArgsConstructor;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class LogoutUseCase implements LogoutUserPort {

  private FindSessionPort findSessionPort;
  private DeleteSessionPort deleteSessionPort;

  @Override
  public void logout(final String token) throws NotFoundException {
    // remove the session
    Session session = findSessionPort.findByToken(token);
    deleteSessionPort.delete(session);
  }
}
