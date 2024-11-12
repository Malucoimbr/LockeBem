package com.code.fullstack_backend.model;

public enum CarroTipo {
    CARRO_PASSEIO("Carro de Passeio"),
    SUV("SUV"),
    CAMINHONETE("Caminhonete");

    private final String nome;

    CarroTipo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
