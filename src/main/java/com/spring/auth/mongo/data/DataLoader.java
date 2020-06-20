package com.spring.auth.mongo.data;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CreateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import com.spring.auth.scope.application.ports.out.CreateScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import com.spring.auth.shared.util.UserUtil;
import com.spring.auth.user.application.ports.out.CreateUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

  private CreateRolePort createRolePort;
  private CreateScopePort createScopePort;
  private CreateUserPort createUserPort;
  private ScopeRepositoryJpa scopeRepositoryJpa;
  private RoleRepositoryJpa roleRepositoryJpa;
  private UserRepositoryJpa userRepositoryJpa;

  public void run(final ApplicationArguments args) throws DuplicatedKeyException {
    if (!isDatabaseEmpty()) return;
    // scope creating
    Scope read =
        createScopePort.create(new Scope("Read scope admin", "Read scope for admins", "READ"));
    Scope create =
        createScopePort.create(
            new Scope("Create scope admin", "Create scope for admins", "CREATE"));
    Scope update =
        createScopePort.create(
            new Scope("Update scope admin", "Update scope for admins", "UPDATE"));
    Scope delete =
        createScopePort.create(
            new Scope("Delete scope admin", "Delete scope for admins", "DELETE"));
    Scope read_user =
        createScopePort.create(new Scope("Read scope users", "Read scope for users", "READ_USER"));
    Scope update_user =
        createScopePort.create(
            new Scope("Update scope users", "Update scope for users", "UPDATE_USER"));
    // role creation
    Role roleAdmin =
        createRolePort.create(
            new Role(
                "Admin role",
                "Admin role",
                "ROLE_ADMIN",
                Arrays.asList(read, create, update, delete)));
    Role roleUser =
        createRolePort.create(
            new Role("User role", "User role", "ROLE_USER", Arrays.asList(read_user, update_user)));
    // user creation
    String adminUsername = "admin";
    String adminEmail = "admin@admin.com";
    String adminPassword = UserUtil.generateRandomPassword();
    User user = new User(adminUsername, adminEmail, adminPassword);
    user.addRoles(Arrays.asList(roleAdmin, roleUser));
    createUserPort.create(user);
    // show info
    System.out.println();
    System.out.println(
        String.format(
            "Using generated admin account, username: %s, email %s, password: %s",
            adminUsername, adminEmail, adminPassword));
    System.out.println();
  }

  private boolean isDatabaseEmpty() {
    if (this.scopeRepositoryJpa.count() > 0) return false;
    if (this.roleRepositoryJpa.count() > 0) return false;
    if (this.userRepositoryJpa.count() > 0) return false;
    return true;
  }
}
