package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.out.UpdateUsersPort;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UpdateUsersRolesRepositoryTest {

  @InjectMocks UpdateUsersRolesRepository updateUsersRolesRepository;
  @Mock UpdateUsersPort updateUsersPort;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

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
  @SneakyThrows
  public void update() {

    Role role1 = randomObjectFiller.createAndFill(Role.class);
    Role role2 = randomObjectFiller.createAndFill(Role.class);
    Role role3 = randomObjectFiller.createAndFill(Role.class);
    Role role4 = randomObjectFiller.createAndFill(Role.class);

    User user1 = randomObjectFiller.createAndFill(User.class);
    user1.addRoles(Arrays.asList(role1, role2, role3, role4));
    User user2 = randomObjectFiller.createAndFill(User.class);
    user2.addRoles(Arrays.asList(role2, role3, role4));
    User user3 = randomObjectFiller.createAndFill(User.class);
    user3.addRoles(Arrays.asList(role3, role4));
    List<User> users = Arrays.asList(user1, user2, user3);

    Role updatedRole1 = SerializationUtils.clone(role1);
    objectFiller.replace(updatedRole1, "name", "updated_role_name1");
    Role updatedRole2 = SerializationUtils.clone(role2);
    objectFiller.replace(updatedRole2, "name", "updated_role_name2");
    List<Role> rolesForUpdate = Arrays.asList(updatedRole1, updatedRole2);

    when(updateUsersPort.updateAll(Mockito.anyList())).then(AdditionalAnswers.returnsFirstArg());

    User updatedUser1 = SerializationUtils.clone(user1);
    objectFiller.replace(
        updatedUser1, "roles", Arrays.asList(updatedRole1, updatedRole2, role3, role4));
    User updatedUser2 = SerializationUtils.clone(user2);
    objectFiller.replace(updatedUser2, "roles", Arrays.asList(updatedRole2, role3, role4));

    List<User> expectedResponse = Arrays.asList(updatedUser1, updatedUser2, user3);
    List<User> response = updateUsersRolesRepository.update(users, rolesForUpdate);
    assertEquals(expectedResponse.toString(), response.toString());
  }
}
