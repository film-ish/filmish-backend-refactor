package com.filmish.backend.auth.domain;

import com.filmish.backend.auth.support.error.AuthErrorType;
import com.filmish.backend.auth.support.error.AuthException;
import org.springframework.stereotype.Component;

@Component
public class AuthUserValidator {

  private final AuthUserRepository authUserRepository;

  public AuthUserValidator(AuthUserRepository authUserRepository) {
    this.authUserRepository = authUserRepository;
  }

  void validateUsernameUnique(String username) {
    if (authUserRepository.existsByUsername(username)) {
      throw new AuthException(AuthErrorType.USERNAME_DUPLICATED, username);
    }
  }

  void validateNicknameUnique(String nickname) {

    if (authUserRepository.existsByNickname(nickname)) {
      throw new AuthException(AuthErrorType.NICKNAME_DUPLICATED, nickname);
    }
  }
}
