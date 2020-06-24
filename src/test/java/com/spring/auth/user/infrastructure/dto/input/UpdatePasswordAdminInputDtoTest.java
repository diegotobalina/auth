package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.RandomObjectFiller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
