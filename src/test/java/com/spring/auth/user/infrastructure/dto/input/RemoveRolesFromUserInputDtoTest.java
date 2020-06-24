package com.spring.auth.user.infrastructure.dto.input;

import com.spring.auth.RandomObjectFiller;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RemoveRolesFromUserInputDtoTest {
  RandomObjectFiller randomObjectFiller = new RandomObjectFiller();

  @Test
  @SneakyThrows
  public void correctOutput() {
    RemoveRolesFromUserInputDto removeRolesFromUserInputDto = new RemoveRolesFromUserInputDto();
    removeRolesFromUserInputDto.setRoleIds(Arrays.asList("1", "2", "3"));
    String expectedResponse =
        String.format(
            "RemoveRolesFromUserInputDto(roleIds=[%s, %s, %s])",
            removeRolesFromUserInputDto.getRoleIds().get(0),
            removeRolesFromUserInputDto.getRoleIds().get(1),
            removeRolesFromUserInputDto.getRoleIds().get(2));
    String response = removeRolesFromUserInputDto.toString();
    assertEquals(expectedResponse, response);
  }
}
