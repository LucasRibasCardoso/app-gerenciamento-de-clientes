package com.agencia.backend.domain.entity;

import com.agencia.backend.domain.entity.enuns.Role;
import java.util.Set;
import java.util.UUID;

public class User {

  private UUID id;
  private String username;
  private String password;

  private Set<Role> roles;

  public User(UUID id, String username, String password, Set<Role> roles) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

}
