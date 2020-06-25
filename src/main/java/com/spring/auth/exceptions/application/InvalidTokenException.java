package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class InvalidTokenException extends Exception {
  public InvalidTokenException(final String errorMessage) {
    super(errorMessage);
  }
}
