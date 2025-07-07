package com.filmish.backend.auth.service;

import com.filmish.backend.storage.db.core.AuthUser;
import com.filmish.backend.storage.db.core.AuthUserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUserDetailsService implements UserDetailsService {

  private final AuthUserJpaRepository authUserJpaRepository;

  public AuthUserDetailsService(AuthUserJpaRepository authUserJpaRepository) {
    this.authUserJpaRepository = authUserJpaRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AuthUser authUser = authUserJpaRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    return new AuthUserDetails(authUser.getId(), authUser.getUsername(), authUser.getPassword());
  }
}
