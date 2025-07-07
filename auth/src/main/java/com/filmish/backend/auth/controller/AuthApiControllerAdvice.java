package com.filmish.backend.auth.controller;

import com.filmish.backend.auth.support.error.AuthException;
import com.filmish.backend.auth.support.response.AuthApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = AuthApiControllerAdvice.class)
public class AuthApiControllerAdvice {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<AuthApiResponse<?>> handleCoreException(AuthException e) {
    switch (e.getErrorType().getLevel()) {
      case ERROR:
        log.error("AuthException : {}", e.getMessage(), e);
      case WARN:
        log.warn("AuthException : {}", e.getMessage(), e);
      default:
        log.info("AuthException : {}", e.getMessage(), e);
    }

    return new ResponseEntity<>(
        AuthApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getHttpStatus());
  }
}
