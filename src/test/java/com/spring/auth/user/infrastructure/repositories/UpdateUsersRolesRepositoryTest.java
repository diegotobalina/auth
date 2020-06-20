package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.out.UpdateUsersPort;
import com.spring.auth.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UpdateUsersRolesRepositoryTest {

  @InjectMocks UpdateUsersRolesRepository updateUsersRolesRepository;
  @Mock UpdateUsersPort updateUsersPort;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(updateUsersRolesRepository);
    assertNotNull(updateUsersPort);
  }

  @Test
  public void update() throws DuplicatedKeyException {

    // roles for the user 1
    Role role1 = new Role("1", "", "", "", new ArrayList<>());
    Role role2 = new Role("2", "", "", "", new ArrayList<>());
    Role role3 = new Role("3", "", "", "", new ArrayList<>());
    Role role4 = new Role("4", "", "", "", new ArrayList<>());
    List<Role> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);
    roles.add(role3);
    roles.add(role4);

    // users
    User user1 = new User("1", "", "", "", roles, new ArrayList<>(), 10);
    User user2 = new User("2", "", "", "", new ArrayList<>(), new ArrayList<>(), 10);
    List<User> users = new ArrayList<>();
    users.add(user1);
    users.add(user2);

    // update 2 of the roles in the user 1
    Role updatedRole1 = new Role("1", "name", "", "", new ArrayList<>());
    Role updatedRole2 = new Role("2", "name", "", "", new ArrayList<>());
    List<Role> updatedRoles = new ArrayList<>();
    updatedRoles.add(updatedRole1);
    updatedRoles.add(updatedRole2);

    // mock database call
    when(updateUsersPort.updateAll(Mockito.anyList())).then(AdditionalAnswers.returnsFirstArg());

    // execute the method
    List<User> response = updateUsersRolesRepository.update(users, updatedRoles);
    User responseUser1 = response.get(0);
    User responseUser2 = response.get(1);

    // check if only the 2 roles in the user 1 has been updated
    assertEquals("name", responseUser1.getRoleById("1").getName());
    assertEquals("name", responseUser1.getRoleById("2").getName());
    assertEquals("", responseUser1.getRoleById("3").getName());
    assertEquals("", responseUser1.getRoleById("4").getName());
    assertEquals(0, responseUser2.getRoles().size());
    assertEquals(0, responseUser2.getRoles().size());
  }
}
