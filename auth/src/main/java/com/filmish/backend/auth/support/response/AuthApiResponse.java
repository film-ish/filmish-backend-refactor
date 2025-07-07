package com.filmish.backend.auth.support.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.filmish.backend.auth.support.error.AuthErrorMessage;
import com.filmish.backend.auth.support.error.AuthErrorType;

public class AuthApiResponse<S> {

  private final AuthResultType result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final S data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final AuthErrorMessage error;

  private AuthApiResponse(AuthResultType result, S data, AuthErrorMessage error) {
    this.result = result;
    this.data = data;
    this.error = error;
  }

  public static AuthApiResponse<Void> success() {
    return new AuthApiResponse<>(AuthResultType.SUCCESS, null, null);
  }

  public static <S> AuthApiResponse<S> success(S data) {
    return new AuthApiResponse<>(AuthResultType.SUCCESS, data, null);
  }

  public static AuthApiResponse<?> error(AuthErrorType error) {
    return new AuthApiResponse<>(AuthResultType.ERROR, null, new AuthErrorMessage(error));
  }

  public static AuthApiResponse<?> error(AuthErrorType error, Object errorData) {
    return new AuthApiResponse<>(
        AuthResultType.ERROR, null, new AuthErrorMessage(error, errorData));
  }

  public AuthResultType getResult() {
    return result;
  }

  public Object getData() {
    return data;
  }

  public AuthErrorMessage getError() {
    return error;
  }
}
