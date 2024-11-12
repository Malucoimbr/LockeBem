package com.code.fullstack_backend.exception;

public class FilialNotFoundException extends RuntimeException {

    public FilialNotFoundException(String id) {
        super("Não foi possível encontrar a filial com ID: " + id);
    }
}