package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.Instancer;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CheckUsersConstraintsRepositoryTest {

  @InjectMocks CheckUsersConstraintsRepository repository;
  @Mock UserRepositoryJpa userRepositoryJpa;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void check() throws DuplicatedKeyException {
    when(userRepositoryJpa.existsByEmailAndIdNot(any(), any())).thenReturn(false);
    when(userRepositoryJpa.existsByUsernameInAndIdNotIn(any(), any())).thenReturn(false);

    Instancer instancer = new Instancer();
    User user = instancer.user();
    repository.check(user);
    repository.check(List.of(user, user));
  }

  @Test
  void check_DuplicatedEmail() {
    when(userRepositoryJpa.existsByEmailInAndIdNotIn(any(), any())).thenReturn(true);
    when(userRepositoryJpa.existsByUsernameInAndIdNotIn(any(), any())).thenReturn(false);

    Instancer instancer = new Instancer();
    User user = instancer.user();
    Assertions.assertThrows(DuplicatedKeyException.class, () -> repository.check(user));
    Assertions.assertThrows(
        DuplicatedKeyException.class, () -> repository.check(List.of(user, user)));
  }

  @Test
  void check_DuplicatedUsername() {
    when(userRepositoryJpa.existsByEmailAndIdNot(any(), any())).thenReturn(false);
    when(userRepositoryJpa.existsByUsernameInAndIdNotIn(any(), any())).thenReturn(true);

    Instancer instancer = new Instancer();
    User user = instancer.user();
    Assertions.assertThrows(DuplicatedKeyException.class, () -> repository.check(user));
    Assertions.assertThrows(
        DuplicatedKeyException.class, () -> repository.check(List.of(user, user)));
  }
}
