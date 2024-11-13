package com.code.fullstack_backend.model;

import java.time.LocalDate;

public class ContratoAluguel {

    private int id;
    private int cliente_id;
    private int carro_id;
    private LocalDate data_inicio;
    private LocalDate data_fim;
    private Double valor_pago;

    public ContratoAluguel() {}

    public ContratoAluguel(int cliente_id, int carro_id, LocalDate data_inicio, LocalDate data_fim, Double valor_pago) {
        this.cliente_id = cliente_id;
        this.carro_id = carro_id;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.valor_pago = valor_pago;
    }


    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getCarro_id() {
        return carro_id;
    }

    public void setCarro_id(int carro_id) {
        this.carro_id = carro_id;
    }

    public LocalDate getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }

    public Double getValor_pago() {
        return valor_pago;
    }

    public void setValor_pago(Double valor_pago) {
        this.valor_pago = valor_pago;
    }

    public long calcularDuracao() {
        return java.time.temporal.ChronoUnit.DAYS.between(data_inicio, data_fim);
    }
}
