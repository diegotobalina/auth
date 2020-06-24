package com.spring.auth.user.infrastructure.dto.output;

import com.spring.auth.RandomObjectFiller;
import com.spring.auth.user.domain.User;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindAllUsersOutputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    User user = randomObjectFiller.createAndFill(User.class);
    FindAllUsersOutputDto findAllUsersOutputDto = new FindAllUsersOutputDto(user);
    String expectedResponse =
        String.format(
            "FindAllUsersOutputDto(id=%s, username=%s, email=%s, roles=[], scopes=[], maxSessions=%d)",
            user.getId(), user.getUsername(), user.getEmail(), user.getMaxSessions());
    String response = findAllUsersOutputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
