package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class WrongPasswordException extends Exception {
  public WrongPasswordException(final String errorMessage) {
    super(errorMessage);
  }
}
