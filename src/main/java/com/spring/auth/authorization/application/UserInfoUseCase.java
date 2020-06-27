package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.UserInfoPort;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.UserUtil;
import lombok.AllArgsConstructor;

import java.security.Principal;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class UserInfoUseCase implements UserInfoPort {

  private FindUserPort findUserPort;

  @Override
  public User userInfo(Principal principal) throws NotFoundException, LockedUserException {
    // get the user id from the memory
    String userId = UserUtil.getUserIdFromPrincipal(principal);
    // find user details from the database
    User user = findUserPort.findById(userId);
    // check if the user is locked
    if (user.isLocked()) throw new LockedUserException("this user is locked");
    return user;
  }
}
