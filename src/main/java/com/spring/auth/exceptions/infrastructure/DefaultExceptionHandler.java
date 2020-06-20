package com.spring.auth.exceptions.infrastructure;

import com.spring.auth.exceptions.application.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {
  @ResponseBody
  @ExceptionHandler({DuplicatedKeyException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse duplicatedUsernameException(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.BAD_REQUEST.value();
    String error = HttpStatus.BAD_REQUEST.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({InfiniteLoopException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse infiniteLoopException(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    String error = HttpStatus.INTERNAL_SERVER_ERROR.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({InvalidTokenException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  private ErrorResponse invalidTokenException(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.UNAUTHORIZED.value();
    String error = HttpStatus.UNAUTHORIZED.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse notFoundException(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.BAD_REQUEST.value();
    String error = HttpStatus.BAD_REQUEST.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({WrongPasswordException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse wrongPasswordException(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.FORBIDDEN.value();
    String error = HttpStatus.FORBIDDEN.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({UnknownTokenFormatException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse unknownTokenFormatException(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.BAD_REQUEST.value();
    String error = HttpStatus.BAD_REQUEST.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse constraintViolationExceptionHandler(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.BAD_REQUEST.value();
    String error = HttpStatus.BAD_REQUEST.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  private ErrorResponse exception(
      final HttpServletRequest request, final HttpServletResponse response, final Exception ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.BAD_REQUEST.value();
    String error = HttpStatus.BAD_REQUEST.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({NotLoadedException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ErrorResponse notLoadedExceptionHandler(
      final HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
    String error = HttpStatus.INTERNAL_SERVER_ERROR.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  @ResponseBody
  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.FORBIDDEN)
  private ErrorResponse accessDeniedExceptionHandler(
      final HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) {
    String timestamp = new Timestamp(System.currentTimeMillis()).toString();
    int status = HttpStatus.FORBIDDEN.value();
    String error = HttpStatus.FORBIDDEN.name();
    String message = getMessage(ex);
    String path = request.getRequestURI();
    return new ErrorResponse(timestamp, status, error, message, path);
  }

  private String getMessage(final Exception ex) {
    final String methodName = getMethodName(ExceptionUtils.getRootCause(ex));
    final String rootCause = ExceptionUtils.getRootCauseMessage(ex);
    return String.format("%s, %s", methodName, rootCause);
  }

  private String getMethodName(final Throwable cause) {
    Throwable rootCause = cause;
    while (rootCause.getCause() != null && rootCause.getCause() != rootCause)
      rootCause = rootCause.getCause();
    final String[] classNameSplit = rootCause.getStackTrace()[0].getClassName().split("\\.");
    final String className = classNameSplit[classNameSplit.length - 1];
    final String methodName = rootCause.getStackTrace()[0].getMethodName();
    return String.format("%s.%s", className, methodName);
  }
}
