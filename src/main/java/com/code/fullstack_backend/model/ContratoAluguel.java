package com.code.fullstack_backend.model;

import java.time.LocalDate;

public class ContratoAluguel {
    private int id;
    private int clienteId;
    private int carroId;
    private int funcionarioId; // Novo campo
    private int seguroId;     // Novo campo
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Double valorPago;

    // Construtores
    public ContratoAluguel() {}

    public ContratoAluguel(int clienteId, int carroId, int funcionarioId, int seguroId,
                           LocalDate dataInicio, LocalDate dataFim, Double valorPago) {
        this.clienteId = clienteId;
        this.carroId = carroId;
        this.funcionarioId = funcionarioId;
        this.seguroId = seguroId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorPago = valorPago;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getCarroId() {
        return carroId;
    }

    public void setCarroId(int carroId) {
        this.carroId = carroId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public int getSeguroId() {
        return seguroId;
    }

    public void setSeguroId(int seguroId) {
        this.seguroId = seguroId;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    // Método para calcular a duração do aluguel
    public long calcularDuracao() {
        return java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim);
    }
}
