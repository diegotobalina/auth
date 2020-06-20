package com.spring.auth.exceptions.application;

public class UnknownTokenFormatException extends Exception {
  public UnknownTokenFormatException(final String errorMessage) {
    super(errorMessage);
  }
}
