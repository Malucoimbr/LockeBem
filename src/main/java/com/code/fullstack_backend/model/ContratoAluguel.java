package com.code.fullstack_backend.model;

import java.time.LocalDate;

public class ContratoAluguel {

    private Integer id;
    private Integer Carro_id;
    private Integer Cliente_id;
    private LocalDate data_inicio;
    private LocalDate data_fim;    
    private Double valor_pago;

    public ContratoAluguel() {}

    public ContratoAluguel(Integer id, Integer id_cliente, Integer Carro_id, LocalDate data_inicio, LocalDate data_fim, Double valor_pago) {
        this.id = id;
        this.Cliente_id = Cliente_id;
        this.Carro_id = Carro_id;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.valor_pago = valor_pago;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCliente_id() {
        return Cliente_id;
    }

    public void setCliente_id(Integer Cliente_id) {
        this.Cliente_id = Cliente_id;
    }

    public Integer getCarro_id() {
        return Carro_id;
    }

    public void setCarro_id(Integer Carro_id) {
        this.Carro_id = Carro_id;
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
