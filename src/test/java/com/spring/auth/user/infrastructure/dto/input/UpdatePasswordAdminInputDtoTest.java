package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.RandomObjectFiller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdatePasswordAdminInputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    UpdatePasswordAdminInputDto updatePasswordAdminInputDto =
        randomObjectFiller.createAndFill(UpdatePasswordAdminInputDto.class);
    String expectedResponse =
        String.format(
            "UpdatePasswordAdminInputDto(newPassword=%s)",
            updatePasswordAdminInputDto.getNewPassword());
    String response = updatePasswordAdminInputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
