package com.spring.auth.exceptions.application;

public class NotLoadedException extends Exception {
  public NotLoadedException(final String errorMessage) {
    super(errorMessage);
  }
}
