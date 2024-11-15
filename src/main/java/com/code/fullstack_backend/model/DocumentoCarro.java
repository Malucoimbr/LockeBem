package com.code.fullstack_backend.model;

public class DocumentoCarro {

    private Integer id;
    private Integer  anoFab;
    private String chassi;
    private String modelo;
    private String placa;

    // Construtor padrão (sem parâmetros)
    public DocumentoCarro() {
    }

    // Construtor com parâmetros
    public DocumentoCarro(Integer id, Integer  anoFab, String chassi, String modelo, String placa) {
        this.id = id;
        this.anoFab = anoFab;
        this.chassi = chassi;
        this.modelo = modelo;
        this.placa = placa;
    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnoFab() {
        return anoFab;
    }

    public void setAnoFab(Integer  anoFab) {
        this.anoFab = anoFab;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
