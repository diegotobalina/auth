package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.LogoutUserPort;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.infrastructure.repositories.ports.DeleteSessionPort;
import lombok.AllArgsConstructor;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class LogoutUseCase implements LogoutUserPort {

  private DeleteSessionPort deleteSessionPort;

  @Override
  public void logout(final String token) throws NotFoundException {
    deleteSessionPort.delete(token);
  }
}
