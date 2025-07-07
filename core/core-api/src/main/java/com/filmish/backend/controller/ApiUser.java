package com.filmish.backend.controller;


import com.filmish.backend.domain.User;

public record ApiUser(Long id) {

  public User toUser() {
    return new User(id);
  }
}
