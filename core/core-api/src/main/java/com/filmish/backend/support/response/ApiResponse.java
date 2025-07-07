package com.filmish.backend.support.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.filmish.backend.support.error.CoreErrorType;
import com.filmish.backend.support.error.ErrorMessage;

public class ApiResponse<S> {

  private final ResultType result;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final S data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final ErrorMessage error;

  private ApiResponse(ResultType result, S data, ErrorMessage error) {
    this.result = result;
    this.data = data;
    this.error = error;
  }

  public static ApiResponse<?> success() {
    return new ApiResponse<>(ResultType.SUCCESS, null, null);
  }

  public static <S> ApiResponse<S> success(S data) {
    return new ApiResponse<>(ResultType.SUCCESS, data, null);
  }

  public static ApiResponse<?> error(CoreErrorType error) {
    return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error));
  }

  public static ApiResponse<?> error(CoreErrorType error, Object errorData) {
    return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(error, errorData));
  }

  public ResultType getResult() {
    return result;
  }

  public Object getData() {
    return data;
  }

  public ErrorMessage getError() {
    return error;
  }
}
