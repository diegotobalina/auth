package com.spring.auth.exceptions.application;

public class WrongPasswordException extends Exception {
  public WrongPasswordException(final String errorMessage) {
    super(errorMessage);
  }
}
