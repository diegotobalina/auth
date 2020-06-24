package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.RandomObjectFiller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddRolesToUserInputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    AddRolesToUserInputDto addRolesToUserInputDto = new AddRolesToUserInputDto();
    addRolesToUserInputDto.setRoleIds(Arrays.asList("1", "2", "3"));
    String expectedResponse =
        String.format(
            "AddRolesToUserInputDto(roleIds=[%s, %s, %s])",
            addRolesToUserInputDto.getRoleIds().get(0),
            addRolesToUserInputDto.getRoleIds().get(1),
            addRolesToUserInputDto.getRoleIds().get(2));
    String response = addRolesToUserInputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
