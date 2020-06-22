package com.spring.auth.exceptions.infrastructure;

import com.spring.auth.exceptions.application.GoogleGetInfoException;
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
public class GoogleGetInfoExceptionHandler {

  @ResponseBody
  @ExceptionHandler({GoogleGetInfoException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ErrorResponse googleGetInfoException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.UNAUTHORIZED, ex);
  }
}
