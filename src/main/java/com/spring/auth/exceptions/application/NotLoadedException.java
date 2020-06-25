package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class NotLoadedException extends Exception {
  public NotLoadedException(final String errorMessage) {
    super(errorMessage);
  }
}
