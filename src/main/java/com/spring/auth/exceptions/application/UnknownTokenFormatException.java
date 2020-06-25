package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class UnknownTokenFormatException extends Exception {
  public UnknownTokenFormatException(final String errorMessage) {
    super(errorMessage);
  }
}
