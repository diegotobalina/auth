package com.spring.auth.exceptions.infrastructure;

import com.spring.auth.exceptions.domain.ErrorResponse;
import com.spring.auth.exceptions.util.ExceptionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** @author diegotobalina created on 24/06/2020 */
@ControllerAdvice
public class DefaultExceptionHandler {

  @ResponseBody
  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse exception(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }
}
