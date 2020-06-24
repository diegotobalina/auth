package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.ObjectFiller;
import com.spring.auth.RandomObjectFiller;
import com.spring.auth.session.application.ports.out.FindAllSessionsByUserIdPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
public class LoadUserSessionsRepositoryTest {

  @InjectMocks LoadUserSessionsRepository loadUserSessionsRepository;
  @Mock FindAllSessionsByUserIdPort findAllSessionsByUserIdPort;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();
  ObjectFiller objectFiller = new ObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(loadUserSessionsRepository);
    assertNotNull(findAllSessionsByUserIdPort);
  }

  @Test
  @SneakyThrows
  public void findAll() {
    Session session1 = randomObjectFiller.createAndFill(Session.class);
    Session session2 = randomObjectFiller.createAndFill(Session.class);
    Session session3 = randomObjectFiller.createAndFill(Session.class);
    List<Session> sessions = Arrays.asList(session1, session2, session3);
    when(findAllSessionsByUserIdPort.findAll(Mockito.any())).thenReturn(sessions);

    User user = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user, "sessions", null);

    User expectedResponse = SerializationUtils.clone(user);
    objectFiller.replace(expectedResponse, "sessions", sessions);

    User response = loadUserSessionsRepository.load(user);
    assertEquals(expectedResponse.toString(), response.toString());
  }

  @Test
  @SneakyThrows
  public void findAllEmpty() {
    when(findAllSessionsByUserIdPort.findAll(Mockito.any())).thenReturn(new ArrayList<>());

    User user = randomObjectFiller.createAndFill(User.class);
    objectFiller.replace(user, "sessions", null);

    User expectedResponse = SerializationUtils.clone(user);
    objectFiller.replace(expectedResponse, "sessions", new ArrayList<>());

    User response = loadUserSessionsRepository.load(user);
    assertEquals(expectedResponse.toString(), response.toString());
  }
}
