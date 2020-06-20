package com.spring.auth.user.application.ports.out;

/** Check if there is an user with the username */
public interface ExistsUserByUserNamePort {
  boolean exists(String username);
}
