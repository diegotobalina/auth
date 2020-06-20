package com.spring.auth.exceptions.application;

public class DuplicatedKeyException extends Exception {
  public DuplicatedKeyException(final String errorMessage) {
    super(errorMessage);
  }
}
