package com.spring.auth.exceptions.application;

public class InvalidTokenException extends Exception {
  public InvalidTokenException(final String errorMessage) {
    super(errorMessage);
  }
}
