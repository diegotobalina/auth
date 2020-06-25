package com.spring.auth.user.application.ports.out;

/** @author diegotobalina created on 24/06/2020 */
/** Check if there is an user with the username */
public interface ExistsUserByUserNamePort {
  boolean exists(String username);
}
