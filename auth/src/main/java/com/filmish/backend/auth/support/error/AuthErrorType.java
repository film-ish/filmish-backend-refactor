package com.filmish.backend.auth.support.error;

import org.springframework.http.HttpStatus;

public enum AuthErrorType {
  AUTHENTICATION_FAILED(
      AuthErrorCode.AUE1000, "인증에 실패하였습니다.", HttpStatus.UNAUTHORIZED, AuthErrorLevel.WARN),
  LOGIN_FAILED(
      AuthErrorCode.AUE1001,
      "아이디 또는 비밀번호가 일치하지 않습니다.",
      HttpStatus.UNAUTHORIZED,
      AuthErrorLevel.INFO),
  USERNAME_DUPLICATED(
      AuthErrorCode.AUE1002, "아이디가 중복되었습니다.", HttpStatus.BAD_REQUEST, AuthErrorLevel.INFO),
  NICKNAME_DUPLICATED(
      AuthErrorCode.AUE1003, "닉네임이 중복되었습니다.", HttpStatus.BAD_REQUEST, AuthErrorLevel.INFO);
  private final AuthErrorCode code;
  private final String message;
  private final HttpStatus httpStatus;
  private final AuthErrorLevel level;

  AuthErrorType(AuthErrorCode code, String message, HttpStatus httpStatus, AuthErrorLevel level) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
    this.level = level;
  }

  public AuthErrorCode getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public AuthErrorLevel getLevel() {
    return level;
  }
}
