package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class LockedUserException extends Exception {
  public LockedUserException(final String errorMessage) {
    super(errorMessage);
  }
}
