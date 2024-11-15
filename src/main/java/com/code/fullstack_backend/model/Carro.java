package com.code.fullstack_backend.model;

public class Carro {

    private Integer id;
    private int km;
    private String carroTipo;
    private double valorDiaria;
    private int filialId;
    private int documentoCarroId;

    public Carro(Integer id, int km, String carroTipo, double valorDiaria, int filialId, int documentoCarroId) {
        this.id = id;
        this.km = km;
        this.carroTipo = carroTipo;
        this.valorDiaria = valorDiaria;
        this.filialId = filialId;
        this.documentoCarroId = documentoCarroId;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getCarroTipo() {
        return carroTipo;
    }

    public void setCarroTipo(String carroTipo) {
        this.carroTipo = carroTipo;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public int getFilialId() {
        return filialId;
    }

    public void setFilialId(int filialId) {
        this.filialId = filialId;
    }

    public int getDocumentoCarroId() {
        return documentoCarroId;
    }

    public void setDocumentoCarroId(int documentoCarroId) {
        this.documentoCarroId = documentoCarroId;
    }
}
