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
class UpdateUsersRoleRepositoryTest {

  @InjectMocks UpdateUsersRoleRepository updateUsersRoleRepository;
  @Mock UpdateUsersPort updateUsersPort;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(updateUsersRoleRepository);
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
    List<User> users = Arrays.asList(user1, user2);

    Role roleForUpdate = SerializationUtils.clone(role1);
    objectFiller.replace(roleForUpdate, "name", "updated_role_name");

    when(updateUsersPort.updateAll(Mockito.anyList())).then(AdditionalAnswers.returnsFirstArg());

    User updatedUser1 = SerializationUtils.clone(user1);
    objectFiller.replace(updatedUser1, "roles", Arrays.asList(role2, role3, role4, roleForUpdate));
    List<User> expectedResponse = Arrays.asList(updatedUser1, user2);
    List<User> response = updateUsersRoleRepository.update(users, roleForUpdate);
    assertEquals(expectedResponse.toString(), response.toString());
  }
}
