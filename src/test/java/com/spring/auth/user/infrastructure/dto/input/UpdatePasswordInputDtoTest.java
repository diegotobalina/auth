package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.RandomObjectFiller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdatePasswordInputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    UpdatePasswordInputDto updatePasswordInputDto =
        randomObjectFiller.createAndFill(UpdatePasswordInputDto.class);
    String expectedResponse =
        String.format(
            "UpdatePasswordInputDto(oldPassword=%s, newPassword=%s)",
            updatePasswordInputDto.getOldPassword(), updatePasswordInputDto.getNewPassword());
    String response = updatePasswordInputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
