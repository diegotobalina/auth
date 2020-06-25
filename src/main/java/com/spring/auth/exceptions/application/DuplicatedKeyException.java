package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class DuplicatedKeyException extends Exception {
  public DuplicatedKeyException(final String errorMessage) {
    super(errorMessage);
  }
}
