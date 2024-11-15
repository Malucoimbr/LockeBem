package com.code.fullstack_backend.model;

public class Seguro {

    private Integer id;
    private String cobertura;

    // Construtor padrão
    public Seguro() {}

    // Construtor com parâmetros
    public Seguro(Integer id, String cobertura) {
        this.id = id;
        this.cobertura = cobertura;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }
}
