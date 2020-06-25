package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class NotFoundException extends Exception {
  public NotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
