package com.spring.auth.user.infrastructure.dto.output;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RemoveRolesFromUserOutputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    User user = randomObjectFiller.createAndFill(User.class);
    RemoveRolesFromUserOutputDto removeRolesFromUserOutputDto =
        new RemoveRolesFromUserOutputDto(user);
    String expectedResponse =
        String.format(
            "RemoveRolesFromUserOutputDto(id=%s, username=%s, email=%s, roles=[], scopes=[])",
            user.getId(), user.getUsername(), user.getEmail());
    String response = removeRolesFromUserOutputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
