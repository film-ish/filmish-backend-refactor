package com.filmish.backend.storage.db.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Table(
    name = "`user`",
    indexes = {
      @Index(name = "udx_user_username", columnList = "username", unique = true),
      @Index(name = "udx_user_nickname", columnList = "nickname", unique = true)
    })
@Entity
public class AuthUser extends AuthBaseEntity {

  private String username;
  private String nickname;
  private String password;

  protected AuthUser() {}

  public AuthUser(String username, String nickname, String password) {
    this.username = username;
    this.nickname = nickname;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getNickname() {
    return nickname;
  }

  public String getPassword() {
    return password;
  }
}
