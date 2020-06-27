package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.anotations.components.controllers.UserController;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.in.UnlockUserPort;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/** @author diegotobalina created on 24/06/2020 */
@UserController
@AllArgsConstructor
public class UnlockUserController {

  private UnlockUserPort unlockUserPort;

  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "Authorization",
        value = "jwt",
        dataType = "string",
        paramType = "header",
        required = true)
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Unlock user", notes = "Desbloquea un usuario")
  @PutMapping("/{userId}/unlock")
  @PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','UPDATE')")
  public void lockUser(@PathVariable("userId") String userId)
      throws DuplicatedKeyException, NotFoundException {
    unlockUserPort.unlock(userId);
  }
}
