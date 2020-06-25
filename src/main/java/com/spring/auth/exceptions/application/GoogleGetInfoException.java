package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class GoogleGetInfoException extends Exception {
  public GoogleGetInfoException(final String errorMessage) {
    super(errorMessage);
  }
}
