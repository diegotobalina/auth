package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CreateUserRepositoryTest {

  @InjectMocks CreateUserRepository createUserRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(createUserRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void create() {
    mockExistsByUsername(false);
    mockExistsByEmail(false);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user1, "id", null);
    objectFiller.replace(user1, "sessions", null);

    User expectedResponse = SerializationUtils.clone(user1);
    User response = createUserRepository.create(user1);
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void createIdMustBeNull() {
    mockExistsByUsername(false);
    mockExistsByEmail(false);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user1, "sessions", null);

    User expectedResponse = SerializationUtils.clone(user1);
    assertThrows(IllegalArgumentException.class, () -> createUserRepository.create(user1));
  }

  @Test
  @SneakyThrows
  public void createDuplicatedKeyExceptionUsername() {
    mockExistsByUsername(true);
    mockExistsByEmail(false);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user1, "id", null);
    objectFiller.replace(user1, "sessions", null);

    assertThrows(DuplicatedKeyException.class, () -> createUserRepository.create(user1));
  }

  @Test
  @SneakyThrows
  public void createDuplicatedKeyExceptionEmail() {
    mockExistsByUsername(false);
    mockExistsByEmail(true);
    mockSave();

    User user1 = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user1, "id", null);
    objectFiller.replace(user1, "sessions", null);

    assertThrows(DuplicatedKeyException.class, () -> createUserRepository.create(user1));
  }

  private void mockExistsByEmail(boolean b) {
    when(userRepositoryJpa.existsByEmail(Mockito.anyString())).thenReturn(b);
  }

  private void mockExistsByUsername(boolean b) {
    when(userRepositoryJpa.existsByUsername(Mockito.anyString())).thenReturn(b);
  }

  private void mockSave() {
    when(userRepositoryJpa.save(Mockito.any())).then(AdditionalAnswers.returnsFirstArg());
  }
}
