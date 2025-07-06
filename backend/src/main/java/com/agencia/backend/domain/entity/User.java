package com.agencia.backend.domain.entity;

import com.agencia.backend.domain.entity.enuns.Role;

import java.util.UUID;

public class User {

    private UUID id;
    private String username;
    private String password;

    private Role role;

    public User(UUID id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

}
