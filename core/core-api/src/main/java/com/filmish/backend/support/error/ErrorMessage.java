package com.filmish.backend.support.error;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorMessage {

  private final String code;

  private final String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final Object data;

  public ErrorMessage(CoreErrorType errorType) {
    this.code = errorType.getCode().name();
    this.message = errorType.getMessage();
    this.data = null;
  }

  public ErrorMessage(CoreErrorType errorType, Object data) {
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
