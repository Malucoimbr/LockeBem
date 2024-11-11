package com.code.fullstack_backend.exception;

public class ClienteNotFoundException extends RuntimeException {
    public ClienteNotFoundException(Long id) {
        super("Cliente with id " + id + " not found");
    }
}