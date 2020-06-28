package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class EmailDoesNotExistsException extends Exception {
  public EmailDoesNotExistsException(final String errorMessage) {
    super(errorMessage);
  }
}
