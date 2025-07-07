package com.filmish.backend.auth.domain;

import org.springframework.stereotype.Service;

@Service
public class SignupService {

  private final AuthUserAppender authUserAppender;

  public SignupService(AuthUserAppender authUserAppender) {
    this.authUserAppender = authUserAppender;
  }

  public void signUp(NewAuthUser newAuthUser) {
    authUserAppender.append(newAuthUser);
  }
}
