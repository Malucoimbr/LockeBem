package com.code.fullstack_backend.model;

import java.time.LocalDate;

public class Funcionario {

    private Integer id;
    private String nome;
    private String cargo;
    private String telefone;
    private LocalDate dataAdmissao;
    private String email;
    private Integer filialId;
    private Integer supervisorId;

    public Funcionario() {}

    public Funcionario(Integer id, String nome, String cargo, String telefone, LocalDate dataAdmissao, String email, Integer filialId, Integer supervisorId) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.telefone = telefone;
        this.dataAdmissao = dataAdmissao;
        this.email = email;
        this.filialId = filialId;
        this.supervisorId = supervisorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFilialId() {
        return filialId;
    }

    public void setFilialId(Integer filialId) {
        this.filialId = filialId;
    }

    public Integer getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

}
