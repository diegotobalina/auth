package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FindAllUsersRepositoryTest {

  @InjectMocks FindAllUsersRepository findAllUsersRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(findAllUsersRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void findAll() {
    UserJpa userJpa1 = randomObjectFiller.createAndFill(UserJpa.class);
    UserJpa userJpa2 = randomObjectFiller.createAndFill(UserJpa.class);
    List<UserJpa> userJpaList = Arrays.asList(userJpa1, userJpa2);
    when(userRepositoryJpa.findAll()).thenReturn(userJpaList);

    List<User> expectedResponse =
        userJpaList.stream().map(UserMapper::parse).collect(Collectors.toList());
    List<User> response = findAllUsersRepository.findAll();
    assertEquals(expectedResponse.toString(), response.toString());
  }
}
