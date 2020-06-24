package com.spring.auth.user.infrastructure.dto.output;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterOutputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    User user = randomObjectFiller.createAndFill(User.class);
    RegisterOutputDto registerOutputDto = new RegisterOutputDto(user);
    String expectedResponse =
        String.format(
            "RegisterOutputDto(userId=%s, username=%s, email=%s)",
            user.getId(), user.getUsername(), user.getEmail());
    String response = registerOutputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
