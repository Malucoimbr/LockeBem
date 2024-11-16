package com.code.fullstack_backend.model;
import java.time.LocalDate;

public class Manutencao {

    private Integer id;
    private LocalDate dataMan;
    private String tipoMan;
    private Double custoMan;
    private Integer funcionarioId;
    private Integer carroId;

    public Manutencao() {}

    public Manutencao(Integer id, LocalDate dataMan, String tipoMan, Double custoMan, Integer funcionarioId, Integer carroId) {
        this.id = id;
        this.dataMan = dataMan;
        this.tipoMan = tipoMan;
        this.custoMan = custoMan;
        this.funcionarioId = funcionarioId;
        this.carroId = carroId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataMan() {
        return dataMan;
    }

    public void setDataMan(LocalDate dataMan) {
        this.dataMan = dataMan;
    }

    public String getTipoMan() {
        return tipoMan;
    }

    public void setTipoMan(String tipoMan) {
        this.tipoMan = tipoMan;
    }

    public Double getCustoMan() {
        return custoMan;
    }

    public void setCustoMan(Double custoMan) {
        this.custoMan = custoMan;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Integer getCarroId() {
        return carroId;
    }

    public void setCarroId(Integer carroId) {
        this.carroId = carroId;
    }
}
