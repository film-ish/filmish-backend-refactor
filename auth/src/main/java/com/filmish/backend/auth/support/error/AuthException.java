package com.filmish.backend.auth.support.error;

public class AuthException extends RuntimeException {
  private final AuthErrorType errorType;

  private final Object data;

  public AuthException(AuthErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.data = null;
  }

  public AuthException(AuthErrorType errorType, Object data) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.data = data;
  }

  public AuthErrorType getErrorType() {
    return errorType;
  }

  public Object getData() {
    return data;
  }
}
