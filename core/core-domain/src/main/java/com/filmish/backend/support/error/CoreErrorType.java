package com.filmish.backend.support.error;

public enum CoreErrorType {
  NOT_FOUND_DATA(
      CoreErrorKind.CLIENT_ERROR, CoreErrorCode.E1000, "해당 데이터를 찾지 못했습니다.", CoreErrorLevel.INFO);

  private final CoreErrorKind kind;
  private final CoreErrorCode code;
  private final String message;
  private final CoreErrorLevel level;

  CoreErrorType(CoreErrorKind kind, CoreErrorCode code, String message, CoreErrorLevel level) {
    this.kind = kind;
    this.code = code;
    this.message = message;
    this.level = level;
  }

  public CoreErrorKind getKind() {
    return kind;
  }

  public CoreErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public CoreErrorLevel getLevel() {
    return level;
  }
}
