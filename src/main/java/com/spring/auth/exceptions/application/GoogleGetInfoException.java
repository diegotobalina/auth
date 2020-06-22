package com.spring.auth.exceptions.application;

public class GoogleGetInfoException extends Exception {
  public GoogleGetInfoException(final String errorMessage) {
    super(errorMessage);
  }
}
