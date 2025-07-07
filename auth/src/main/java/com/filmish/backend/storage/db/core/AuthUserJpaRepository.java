package com.filmish.backend.storage.db.core;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserJpaRepository extends JpaRepository<AuthUser, Long> {

  Optional<AuthUser> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByNickname(String nickname);
}
