package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
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

public class FindUserByEmailTest {

  @InjectMocks FindUserByEmailRepository findUserByEmailRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(findUserByEmailRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void find() {
    UserJpa userJpa = randomObjectFiller.createAndFill(UserJpa.class);
    when(userRepositoryJpa.findByEmail(Mockito.any())).thenReturn(Optional.of(userJpa));

    User expectedResponse = UserMapper.parse(userJpa);
    User response = findUserByEmailRepository.find("email");
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void findNotFoundException() {
    when(userRepositoryJpa.findById(Mockito.any())).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> findUserByEmailRepository.find("email"));
  }
}
