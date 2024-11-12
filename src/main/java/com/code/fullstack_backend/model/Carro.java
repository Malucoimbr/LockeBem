package com.code.fullstack_backend.model;

public class Carro {

    private Long id;
    private String placa;
    private String modelo;
    private int ano_fab;
    private int km;
    private CarroTipo carroTipo;
    private int filialId;

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno_fab() { // Alterado para getAno_fab
        return ano_fab;
    }

    public void setAno_fab(int ano_fab) { // Alterado para setAno_fab
        this.ano_fab = ano_fab;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public CarroTipo getCarroTipo() {
        return carroTipo;
    }

    public void setCarroTipo(String carroTipo) {
        this.carroTipo = CarroTipo.valueOf(carroTipo);
    }

    public int getFilialId() {
        return filialId;
    }

    public void setFilialId(int filialId) {
        this.filialId = filialId;
    }


}
