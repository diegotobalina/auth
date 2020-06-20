package com.spring.auth.user.application.ports.out;

/** Check if there is an user with the email */
public interface ExistsUserByEmailPort {
  boolean exists(String email);
}
