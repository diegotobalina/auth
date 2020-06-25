package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.UserInfoPort;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.UserUtil;
import lombok.AllArgsConstructor;

import java.security.Principal;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class UserInfoUseCase implements UserInfoPort {

  private FindUserByIdPort findUserByIdPort;

  @Override
  public User userInfo(Principal principal) throws NotFoundException {
    // get the user id from the memory
    String userId = UserUtil.getUserIdFromPrincipal(principal);
    // find user details from the database
    return findUserByIdPort.find(userId);
  }
}
