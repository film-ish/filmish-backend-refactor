package com.filmish.backend.auth.support.error;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AuthErrorMessage {

  private final String code;

  private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final Object data;

  public AuthErrorMessage(AuthErrorType errorType) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = null;
  }

  public AuthErrorMessage(AuthErrorType errorType, Object data) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Object getData() {
    return data;
  }
}
