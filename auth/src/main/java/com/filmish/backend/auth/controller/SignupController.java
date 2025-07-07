package com.filmish.backend.auth.controller;

import com.filmish.backend.auth.domain.NewAuthUser;
import com.filmish.backend.auth.domain.SignupService;
import com.filmish.backend.auth.support.response.AuthApiResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignupController {

  private final PasswordEncoder passwordEncoder;
  private final SignupService signupService;

  public SignupController(PasswordEncoder passwordEncoder, SignupService signupService) {
    this.passwordEncoder = passwordEncoder;
    this.signupService = signupService;
  }

  @PostMapping("/signup")
  public AuthApiResponse<Void> signup(@RequestBody SignupRequest request) {
    NewAuthUser newAuthUser = request.toNewAuthUser(passwordEncoder);
    signupService.signUp(newAuthUser);
    return AuthApiResponse.success();
  }
}
