package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.application.ports.in.RemoveRolesFromUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.input.AddRolesToUserInputDto;
import com.spring.auth.user.infrastructure.dto.output.AddRolesToUserOutputDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RemoveRolesFromUserControllerTest {

  @InjectMocks RemoveRolesFromUserController removeRolesFromUserController;
  @Mock RemoveRolesFromUserPort removeRolesFromUserPort;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(removeRolesFromUserController);
    assertNotNull(removeRolesFromUserPort);
  }

  @Test
  @SneakyThrows
  void findAll() {
    String userId = "user_id";
    AddRolesToUserInputDto addRolesToUserInputDto =
        randomObjectFiller.createAndFill(AddRolesToUserInputDto.class);
    User updatedUser = randomObjectFiller.createAndFill(User.class);
    when(removeRolesFromUserPort.remove(anyString(), anyList())).thenReturn(updatedUser);
    AddRolesToUserOutputDto expectedResponse = new AddRolesToUserOutputDto(updatedUser);
    AddRolesToUserOutputDto response =
        removeRolesFromUserController.findAll(userId, addRolesToUserInputDto);
    assertEquals(expectedResponse.toString(), response.toString());
  }
}
