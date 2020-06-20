package com.spring.auth.exceptions.application;

public class InfiniteLoopException extends Exception {
  public InfiniteLoopException(final String errorMessage) {
    super(errorMessage);
  }
}
