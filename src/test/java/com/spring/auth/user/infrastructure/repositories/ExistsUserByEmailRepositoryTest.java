package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ExistsUserByEmailRepositoryTest {

  @InjectMocks ExistsUserByEmailRepository existsUserByEmailRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(existsUserByEmailRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void exists() {
    when(userRepositoryJpa.existsByEmail(Mockito.anyString())).thenReturn(true);
    boolean response = existsUserByEmailRepository.exists("email");
    assertEquals(true, response);
  }

  @Test
  @SneakyThrows
  public void notExists() {
    when(userRepositoryJpa.existsByEmail(Mockito.anyString())).thenReturn(false);
    boolean response = existsUserByEmailRepository.exists("email");
    assertEquals(false, response);
  }
}
