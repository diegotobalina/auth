package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.RandomObjectFiller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterInputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    RegisterInputDto registerInputDto = new RegisterInputDto();
    registerInputDto.setUsername("adsasddsaf");
    registerInputDto.setEmail("test@test.com");
    registerInputDto.setPassword("123adsdad");
    String expectedResponse =
        String.format(
            "RegisterInputDto(username=%s, email=%s, password=%s)",
            registerInputDto.getUsername(),
            registerInputDto.getEmail(),
            registerInputDto.getPassword());
    String response = registerInputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
