package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.out.FindRoleByValuePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.in.RegisterUserPort;
import com.spring.auth.user.application.ports.out.CreateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@UseCase
@AllArgsConstructor
public class RegisterUserUseCase implements RegisterUserPort {

  private CreateUserPort createUserPort;
  private FindRoleByValuePort findRoleByValuePort;

  /**
   * Create a new user in the database
   *
   * @param username Username for the new user
   * @param email Email for the new user
   * @param password Password for the new user
   * @return Created user
   * @throws DuplicatedKeyException If there is a problem saving the user like duplicated username
   * @throws NotFoundException If some default role was not found
   */
  @Override
  public User register(String username, String email, String password)
      throws DuplicatedKeyException, NotFoundException {

    List<Role> roles = this.getDefaultRoles();
    User user = new User(username, email, password);
    user.addRoles(roles);
    return createUserPort.create(user);
  }

  private List<Role> getDefaultRoles() throws NotFoundException {
    return Collections.singletonList(findRoleByValuePort.find("ROLE_USER"));
  }
}
