package com.filmish.backend.controller;

import com.filmish.backend.support.error.CoreErrorKind;
import com.filmish.backend.support.error.CoreException;
import com.filmish.backend.support.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = ApiControllerAdvice.class)
public class ApiControllerAdvice {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(CoreException.class)
  public ResponseEntity<ApiResponse<?>> handleCoreException(CoreException e) {
    switch (e.getErrorType().getLevel()) {
      case ERROR:
        log.error("CoreException : {}", e.getMessage(), e);
      case WARN:
        log.warn("CoreException : {}", e.getMessage(), e);
      default:
        log.info("CoreException : {}", e.getMessage(), e);
    }

    HttpStatus status = e.getErrorType().getKind() == CoreErrorKind.CLIENT_ERROR
        ? HttpStatus.BAD_REQUEST
        : HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), status);
  }
}
