package com.spring.auth.user.infrastructure.dto.output;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddRolesToUserOutputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    User user = randomObjectFiller.createAndFill(User.class);
    AddRolesToUserOutputDto addRolesToUserOutputDto = new AddRolesToUserOutputDto(user);
    String expectedResponse =
        String.format(
            "AddRolesToUserOutputDto(id=%s, username=%s, email=%s, roles=[], scopes=[])",
            user.getId(), user.getUsername(), user.getEmail());
    String response = addRolesToUserOutputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
