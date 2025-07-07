package com.filmish.backend.auth.domain;

public interface AuthUserRepository {

  void save(NewAuthUser newAuthUser);

  boolean existsByUsername(String username);

  boolean existsByNickname(String nickname);
}
