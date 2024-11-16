package com.code.fullstack_backend.model;
import java.time.LocalDate;

public class Multa {

    private Integer id;
    private LocalDate dataMulta;
    private String tipoInfracao;
    private Double valorMulta;
    private Integer contratoId;

    public Multa() {}

    public Multa(Integer id, LocalDate dataMulta, String tipoInfracao, Double valorMulta, Integer contratoId) {
        this.id = id;
        this.dataMulta = dataMulta;
        this.tipoInfracao = tipoInfracao;
        this.valorMulta = valorMulta;
        this.contratoId = contratoId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataMulta() {
        return dataMulta;
    }

    public void setDataMulta(LocalDate dataMulta) {
        this.dataMulta = dataMulta;
    }

    public String getTipoInfracao() {
        return tipoInfracao;
    }

    public void setTipoInfracao(String tipoInfracao) {
        this.tipoInfracao = tipoInfracao;
    }

    public Double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(Double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public Integer getContratoId() {
        return contratoId;
    }

    public void setContratoId(Integer contratoId) {
        this.contratoId = contratoId;
    }
}
