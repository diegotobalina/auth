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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Collections;
import java.util.Hashtable;
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
    if (!doEmailExists(email))
      throw new EmailDoesNotExistsException("email does not exists: " + email);

    List<Role> roles = this.getDefaultRoles();
    User user = new User(username, email, password);
    user.addRoles(roles);
    return createUserPort.create(user);
  }

  private List<Role> getDefaultRoles() throws NotFoundException {
    return Collections.singletonList(findRolePort.findByValue("ROLE_USER"));
  }

  private boolean doEmailExists(String email) {
    String hostName = email.split("@")[1];
    Hashtable env = new Hashtable();
    env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
    try {
      DirContext ictx = new InitialDirContext(env);
      Attributes attrs = ictx.getAttributes(hostName, new String[] {"MX"});
      Attribute attr = attrs.get("MX");
      if ((attr == null) || (attr.size() == 0)) {
        attrs = ictx.getAttributes(hostName, new String[] {"A"});
        attr = attrs.get("A");
        if (attr == null) return false;
      }
      return true;
    } catch (NamingException ex) {
      log.info(ex.getMessage());
      return false;
    }
  }
}
