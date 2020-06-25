package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UpdateUsersRepositoryTest {

  @InjectMocks UpdateUsersRepository updateUsersRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(updateUsersRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void updateAll() {
    mockExistsByUsername(false);
    mockExistsByEmail(false);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    User user2 = randomObjectFiller.createAndFill(User.class);
    User user3 = randomObjectFiller.createAndFill(User.class);

    objectFiller.replace(user1, "sessions", null);
    objectFiller.replace(user2, "sessions", null);
    objectFiller.replace(user3, "sessions", null);

    List<User> users = Arrays.asList(user1, user2, user3);
    List<User> expectedResponse = Arrays.asList(user1, user2, user3);
    List<User> response = updateUsersRepository.updateAll(users);
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void updateAllDuplicatedKeyExceptionUsername() {
    mockExistsByUsername(true);
    mockExistsByEmail(false);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    User user2 = randomObjectFiller.createAndFill(User.class);
    User user3 = randomObjectFiller.createAndFill(User.class);

    objectFiller.replace(user1, "sessions", null);
    objectFiller.replace(user2, "sessions", null);
    objectFiller.replace(user3, "sessions", null);

    List<User> users = Arrays.asList(user1, user2, user3);
    assertThrows(DuplicatedKeyException.class, () -> updateUsersRepository.updateAll(users));
  }

  @Test
  @SneakyThrows
  public void updateAllDuplicatedKeyExceptionEmail() {
    mockExistsByUsername(false);
    mockExistsByEmail(true);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    User user2 = randomObjectFiller.createAndFill(User.class);
    User user3 = randomObjectFiller.createAndFill(User.class);

    objectFiller.replace(user1, "sessions", null);
    objectFiller.replace(user2, "sessions", null);
    objectFiller.replace(user3, "sessions", null);

    List<User> users = Arrays.asList(user1, user2, user3);
    assertThrows(DuplicatedKeyException.class, () -> updateUsersRepository.updateAll(users));
  }

  private void mockExistsByEmail(boolean b) {
    when(userRepositoryJpa.existsByEmailInAndIdNotIn(Mockito.anyList(), Mockito.anyList()))
        .thenReturn(b);
  }

  private void mockExistsByUsername(boolean b) {
    when(userRepositoryJpa.existsByUsernameInAndIdNotIn(Mockito.anyList(), Mockito.anyList()))
        .thenReturn(b);
  }

  private void mockSave() {
    when(userRepositoryJpa.saveAll(Mockito.anyList())).then(AdditionalAnswers.returnsFirstArg());
  }
}
