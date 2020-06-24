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
