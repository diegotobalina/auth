package com.spring.auth.user.infrastructure.dto.output;

import com.spring.auth.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RegisterOutputDto {

  private String userId; // user id of the new user
  private String username; // username of the new user
  private String email; // email of the new user

  public RegisterOutputDto(User user) {
    this.userId = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
  }
}
