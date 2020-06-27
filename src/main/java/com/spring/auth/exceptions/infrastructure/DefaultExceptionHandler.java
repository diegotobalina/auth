package com.spring.auth.exceptions.infrastructure;

import com.spring.auth.exceptions.application.*;
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
import javax.validation.ConstraintViolationException;

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

  @ResponseBody
  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse accessDeniedExceptionHandler(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.FORBIDDEN, ex);
  }

  @ResponseBody
  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse constraintViolationExceptionHandler(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
  }

  @ResponseBody
  @ExceptionHandler({EmailDoesNotExistsException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse emailDoesNotExistsExceptionHandler(
      HttpServletRequest request, HttpServletResponse response, EmailDoesNotExistsException ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
  }

  @ResponseBody
  @ExceptionHandler({DuplicatedKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse duplicatedKeyException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
  }

  @ResponseBody
  @ExceptionHandler({GoogleGetInfoException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ErrorResponse googleGetInfoException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.UNAUTHORIZED, ex);
  }

  @ResponseBody
  @ExceptionHandler({InfiniteLoopException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse infiniteLoopException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  @ResponseBody
  @ExceptionHandler({InvalidTokenException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ErrorResponse invalidTokenException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.UNAUTHORIZED, ex);
  }

  @ResponseBody
  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse notFoundException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
  }

  @ResponseBody
  @ExceptionHandler({NotLoadedException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse notLoadedExceptionHandler(
      HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  @ResponseBody
  @ExceptionHandler({UnknownTokenFormatException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse unknownTokenFormatException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.BAD_REQUEST, ex);
  }

  @ResponseBody
  @ExceptionHandler({WrongPasswordException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse wrongPasswordException(
      HttpServletRequest request, HttpServletResponse response, Exception ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.FORBIDDEN, ex);
  }

  @ResponseBody
  @ExceptionHandler({LockedUserException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse lockedUserException(
      HttpServletRequest request, HttpServletResponse response, LockedUserException ex) {
    return ExceptionUtil.getErrorResponse(request, HttpStatus.FORBIDDEN, ex);
  }
}
