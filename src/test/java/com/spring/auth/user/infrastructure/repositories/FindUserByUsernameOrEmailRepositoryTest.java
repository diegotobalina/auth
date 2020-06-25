package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FindUserByUsernameOrEmailRepositoryTest {

  @InjectMocks FindUserByUsernameOrEmailRepository findUserByUsernameOrEmailRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(findUserByUsernameOrEmailRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void findByUsername() {
    UserJpa userJpa = randomObjectFiller.createAndFill(UserJpa.class);
    when(userRepositoryJpa.findByUsername(Mockito.any())).thenReturn(Optional.of(userJpa));
    when(userRepositoryJpa.findByEmail(Mockito.any())).thenReturn(Optional.empty());

    User expectedResponse = UserMapper.parse(userJpa);
    User response = findUserByUsernameOrEmailRepository.find("username", null);
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void findByEmail() {
    UserJpa userJpa = randomObjectFiller.createAndFill(UserJpa.class);
    when(userRepositoryJpa.findByUsername(Mockito.any())).thenReturn(Optional.empty());
    when(userRepositoryJpa.findByEmail(Mockito.any())).thenReturn(Optional.of(userJpa));

    User expectedResponse = UserMapper.parse(userJpa);
    User response = findUserByUsernameOrEmailRepository.find(null, "email");
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void findNotFoundException() {
    when(userRepositoryJpa.findByUsername(Mockito.any())).thenReturn(Optional.empty());
    when(userRepositoryJpa.findByEmail(Mockito.any())).thenReturn(Optional.empty());

    assertThrows(
        NotFoundException.class, () -> findUserByUsernameOrEmailRepository.find(null, null));
  }
}
