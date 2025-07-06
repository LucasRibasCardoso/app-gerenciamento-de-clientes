package com.agencia.backend.application.services;

import com.agencia.backend.domain.entity.User;

public interface UserFieldUpdateService {
    User updateUser(User existingUser, User userRequest);
}
