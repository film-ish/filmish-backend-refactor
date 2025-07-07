package com.filmish.backend.storage.db.core;

import com.filmish.backend.auth.domain.AuthUserRepository;
import com.filmish.backend.auth.domain.NewAuthUser;
import org.springframework.stereotype.Repository;

@Repository
public class AuthUserCoreRepository implements AuthUserRepository {

  private final AuthUserJpaRepository authUserJpaRepository;

  public AuthUserCoreRepository(AuthUserJpaRepository authUserJpaRepository) {
    this.authUserJpaRepository = authUserJpaRepository;
  }

  @Override
  public void save(NewAuthUser newAuthUser) {
    authUserJpaRepository.save(
        new AuthUser(newAuthUser.username(), newAuthUser.nickname(), newAuthUser.password()));
  }

  @Override
  public boolean existsByUsername(String username) {
    return authUserJpaRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByNickname(String nickname) {
    return authUserJpaRepository.existsByNickname(nickname);
  }
}
