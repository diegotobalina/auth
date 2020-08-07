package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class InvalidAuthorizeParamsException extends Exception {
  public InvalidAuthorizeParamsException(final String errorMessage) {
    super(errorMessage);
  }
}
