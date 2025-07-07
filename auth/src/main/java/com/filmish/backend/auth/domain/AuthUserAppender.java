package com.filmish.backend.auth.domain;

import org.springframework.stereotype.Component;

@Component
public class AuthUserAppender {

  private final AuthUserValidator authUserValidator;
  private final AuthUserRepository authUserRepository;

  public AuthUserAppender(
      AuthUserValidator authUserValidator, AuthUserRepository authUserRepository) {
    this.authUserValidator = authUserValidator;
    this.authUserRepository = authUserRepository;
  }

  public void append(NewAuthUser newAuthUser) {
    authUserValidator.validateUsernameUnique(newAuthUser.username());
    authUserValidator.validateNicknameUnique(newAuthUser.nickname());
    authUserRepository.save(newAuthUser);
  }
}
