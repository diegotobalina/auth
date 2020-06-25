package com.spring.auth.mongo.data;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CreateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import com.spring.auth.scope.application.ports.out.CreateScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import com.spring.auth.user.application.ports.out.CreateUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import com.spring.auth.util.UserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
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
    Scope readScope = new Scope("Read scope admin", "Read scope for admins", "READ");
    Scope createScope = new Scope("Create scope admin", "Create scope for admins", "CREATE");
    Scope updateScope = new Scope("Update scope admin", "Update scope for admins", "UPDATE");
    Scope deleteScope = new Scope("Delete scope admin", "Delete scope for admins", "DELETE");
    Scope readUserScope = new Scope("Read scope users", "Read scope for users", "READ_USER");
    Scope updateUserScope =
        new Scope("Update scope users", "Update scope for users", "UPDATE_USER");

    Scope createdReadScope = createScopePort.create(readScope);
    Scope createdCreateScope = createScopePort.create(createScope);
    Scope createdUpdateScope = createScopePort.create(updateScope);
    Scope createdDeleteScope = createScopePort.create(deleteScope);
    Scope createdReadUserScope = createScopePort.create(readUserScope);
    Scope createdUpdateUserScope = createScopePort.create(updateUserScope);

    List<Scope> createdAdminScopes =
        Arrays.asList(createdReadScope, createdCreateScope, createdUpdateScope, createdDeleteScope);
    List<Scope> createdUserScopes = Arrays.asList(createdReadUserScope, createdUpdateUserScope);

    // role creation
    Role roleAdmin = new Role("Admin role", "Admin role", "ROLE_ADMIN", createdAdminScopes);
    Role roleUser = new Role("User role", "User role", "ROLE_USER", createdUserScopes);

    Role createdRoleAdmin = createRolePort.create(roleAdmin);
    Role createdRoleUser = createRolePort.create(roleUser);

    // adminUser creation
    String adminUsername = "admin";
    String adminEmail = "admin@admin.com";
    String adminPassword = UserUtil.generateRandomPassword();
    User adminUser = new User(adminUsername, adminEmail, adminPassword);
    adminUser.addRoles(Arrays.asList(createdRoleAdmin, createdRoleUser));
    createUserPort.create(adminUser);

    String createdAdminMessage =
        String.format(
            "Using generated admin account, username: %s, email %s, password: %s",
            adminUsername, adminEmail, adminPassword);

    System.out.println();
    System.out.println(createdAdminMessage);
    System.out.println();
  }

  private boolean isDatabaseEmpty() {
    if (this.scopeRepositoryJpa.count() > 0) return false;
    if (this.roleRepositoryJpa.count() > 0) return false;
    if (this.userRepositoryJpa.count() > 0) return false;
    return true;
  }
}
