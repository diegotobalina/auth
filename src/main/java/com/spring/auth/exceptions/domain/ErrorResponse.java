package com.spring.auth.exceptions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** @author diegotobalina created on 24/06/2020 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ErrorResponse {
  private String timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
}
