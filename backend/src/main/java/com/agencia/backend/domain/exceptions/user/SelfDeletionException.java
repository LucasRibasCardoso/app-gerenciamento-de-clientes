package com.agencia.backend.domain.exceptions.user;

public class SelfDeletionException extends RuntimeException {

    public SelfDeletionException(String message) {
        super(message);
    }

}
