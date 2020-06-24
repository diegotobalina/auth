package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
