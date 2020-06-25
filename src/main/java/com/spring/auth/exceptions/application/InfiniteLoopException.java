package com.spring.auth.exceptions.application;

/** @author diegotobalina created on 24/06/2020 */
public class InfiniteLoopException extends Exception {
  public InfiniteLoopException(final String errorMessage) {
    super(errorMessage);
  }
}
