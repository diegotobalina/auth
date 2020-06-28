package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.EmailDoesNotExistsException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.out.FindRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.in.RegisterUserPort;
import com.spring.auth.user.application.ports.out.CreateUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.util.EmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Slf4j
@UseCase
@AllArgsConstructor
public class RegisterUserUseCase implements RegisterUserPort {

  private CreateUserPort createUserPort;
  private FindRolePort findRolePort;

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
      throws DuplicatedKeyException, NotFoundException, EmailDoesNotExistsException {

    if (!EmailUtil.doEmailExists(email))
      throw new EmailDoesNotExistsException("email does not exists: " + email);

    List<Role> roles = this.getDefaultRoles();
    User user = new User(username, email, password);
    user.addRoles(roles);
    return createUserPort.create(user);
  }

  private List<Role> getDefaultRoles() throws NotFoundException {
    return Collections.singletonList(findRolePort.findByValue("ROLE_USER"));
  }
}
