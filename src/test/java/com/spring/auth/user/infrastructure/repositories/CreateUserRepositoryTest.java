package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.Instancer;
import com.spring.auth.ObjectFiller;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.infrastructure.repositories.ports.CheckUsersConstraintsPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateUserRepositoryTest {

  @InjectMocks CreateUserRepository repository;
  @Mock UserRepositoryJpa userRepositoryJpa;
  @Mock CheckUsersConstraintsPort checkUsersConstraintsPort;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void create() throws IllegalAccessException, DuplicatedKeyException {
    ObjectFiller objectFiller = new ObjectFiller();
    Instancer instancer = new Instancer();

    CheckUsersConstraintsPort spy = Mockito.spy(checkUsersConstraintsPort);
    Mockito.doNothing().when(spy).check(Mockito.anyList());
    Mockito.doNothing().when(spy).check(Mockito.any(User.class));
    when(userRepositoryJpa.save(any()))
        .then(
            invocation -> {
              UserJpa userJpa = invocation.getArgument(0);
              objectFiller.replace(userJpa, "id", "generated_id");
              return userJpa;
            });

    User user = instancer.user();
    objectFiller.replace(user, "id", null);

    User response = repository.create(user);

    User expectedResponse = user;
    objectFiller.replace(expectedResponse, "id", "generated_id");

    Assertions.assertEquals(user.toString(), response.toString());
  }

  @Test
  void create_IllegalArgumentException() {
    Instancer instancer = new Instancer();
    User user = instancer.user();
    Assertions.assertThrows(IllegalArgumentException.class, () -> repository.create(user));
  }
}
