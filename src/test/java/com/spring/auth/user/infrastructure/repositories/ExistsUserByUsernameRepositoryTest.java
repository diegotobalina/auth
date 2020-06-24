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
public class ExistsUserByUsernameRepositoryTest {

  @InjectMocks ExistsUserByUsernameRepository existsUserByUsernameRepository;
  @Mock UserRepositoryJpa userRepositoryJpa;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(existsUserByUsernameRepository);
    assertNotNull(userRepositoryJpa);
  }

  @Test
  @SneakyThrows
  public void exists() {
    when(userRepositoryJpa.existsByUsername(Mockito.anyString())).thenReturn(true);
    boolean response = existsUserByUsernameRepository.exists("username");
    assertEquals(true, response);
  }

  @Test
  @SneakyThrows
  public void notExists() {
    when(userRepositoryJpa.existsByUsername(Mockito.anyString())).thenReturn(false);
    boolean response = existsUserByUsernameRepository.exists("username");
    assertEquals(false, response);
  }
}
