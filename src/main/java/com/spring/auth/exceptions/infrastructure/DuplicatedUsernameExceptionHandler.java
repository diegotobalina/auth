package com.spring.auth.exceptions.infrastructure;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.domain.ErrorResponse;
import com.spring.auth.exceptions.util.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class DuplicatedUsernameExceptionHandler {
  @ResponseBody
  @ExceptionHandler({DuplicatedKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse duplicatedUsernameException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
  }
}
