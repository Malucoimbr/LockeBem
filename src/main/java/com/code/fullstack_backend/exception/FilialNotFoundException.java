package com.code.fullstack_backend.exception;

public class FilialNotFoundException extends RuntimeException {

    public FilialNotFoundException(Long id) {
        super("Não foi possível encontrar a filial com ID: " + id);
    }
}