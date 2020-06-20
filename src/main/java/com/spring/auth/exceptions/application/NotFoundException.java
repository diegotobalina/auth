package com.spring.auth.exceptions.application;

public class NotFoundException extends Exception {
  public NotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
