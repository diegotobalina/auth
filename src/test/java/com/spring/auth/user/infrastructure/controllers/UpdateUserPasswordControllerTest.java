package com.spring.auth.user.infrastructure.controllers;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.application.ports.in.UpdateUserPasswordPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.dto.input.UpdatePasswordAdminInputDto;
import com.spring.auth.user.infrastructure.dto.input.UpdatePasswordInputDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;

class UpdateUserPasswordControllerTest {

  @InjectMocks UpdateUserPasswordController updateUserPasswordController;
  @Mock UpdateUserPasswordPort updateUserPasswordPort;
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void init() {
    assertNotNull(updateUserPasswordController);
    assertNotNull(updateUserPasswordPort);
  }

  @Test
  @SneakyThrows
  public void updatePassword() {
    User principal = randomObjectFiller.createAndFill(User.class);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
        new UsernamePasswordAuthenticationToken(principal, null);
    UpdatePasswordInputDto updatePasswordInputDto =
        randomObjectFiller.createAndFill(UpdatePasswordInputDto.class);
    doNothing()
        .when(updateUserPasswordPort)
        .update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    updateUserPasswordController.updatePassword(
        usernamePasswordAuthenticationToken, updatePasswordInputDto);
  }

  @Test
  @SneakyThrows
  public void updatePasswordAdmin() {
    UpdatePasswordAdminInputDto updatePasswordAdminInputDto =
        randomObjectFiller.createAndFill(UpdatePasswordAdminInputDto.class);
    doNothing().when(updateUserPasswordPort).update(Mockito.anyString(), Mockito.anyString());
    updateUserPasswordController.updatePassword("", updatePasswordAdminInputDto);
  }
}
