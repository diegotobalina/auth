package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FindAllUsersRepositoryTest {

  @InjectMocks FindAllUsersRepository findAllUsersRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;

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
  public void findAll() {

    // users that will be returned from the database
    UserJpa userJpa = new UserJpa("1", "", "", "", new ArrayList<>(), new ArrayList<>(), 0);
    List<UserJpa> userJpaList = Arrays.asList(userJpa, userJpa);

    // mock the database call
    when(userRepositoryJpa.findAll()).thenReturn(userJpaList);

    // call the method
    List<User> users = findAllUsersRepository.findAll();

    // check if are the same users count
    assertEquals(userJpaList.size(), users.size());
  }
}
