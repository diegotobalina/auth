package com.spring.auth.session.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.in.DeleteOlderSessionByUserIdPort;
import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.application.ports.out.FindOlderSessionByUserIdPort;
import com.spring.auth.session.domain.Session;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class DeleteOlderSessionByUserIdUseCase implements DeleteOlderSessionByUserIdPort {

  private FindOlderSessionByUserIdPort findOlderSessionByUserIdPort;
  private DeleteSessionPort deleteSessionPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(final String userId) throws NotFoundException {
    Session olderSession = findOlderSessionByUserIdPort.find(userId);
    deleteSessionPort.delete(olderSession);
  }
}
