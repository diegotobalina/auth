package com.spring.auth.exceptions.infrastructure;

import com.spring.auth.exceptions.domain.ErrorResponse;
import com.spring.auth.exceptions.util.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class AccessDeniedExceptionHandler {
  @ResponseBody
  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse accessDeniedExceptionHandler(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.FORBIDDEN, ex);
  }
}
