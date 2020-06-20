package com.spring.auth.authorization.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.authorization.application.ports.in.UserInfoPort;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.shared.util.UserUtil;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

import java.security.Principal;

@UseCase
@AllArgsConstructor
public class UserInfoUseCase implements UserInfoPort {

  private FindUserByIdPort findUserByIdPort;

  @Override
  public User userInfo(final Principal principal) throws NotFoundException {
    // get the user id from the memory
    final String userId = UserUtil.getUserIdFromPrincipal(principal);
    // findAll user details from the database
    return findUserByIdPort.find(userId);
  }
}
