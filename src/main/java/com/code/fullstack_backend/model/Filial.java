package com.code.fullstack_backend.model;

public class Filial {

    private String nome;
    private String cidade;
    private String estado;
    private String rua;
    private String numero;
    private String telefone;
    private String codigoFilial;
    private String cnpj; // Novo campo

    // Default constructor
    public Filial() {}

    // Constructor with all parameters
    public Filial(Long id, String nome, String cidade, String estado, String rua, String numero, String telefone, String codigoFilial, String cnpj) {

        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.rua = rua;
        this.numero = numero;
        this.telefone = telefone;
        this.codigoFilial = codigoFilial;
        this.cnpj = cnpj; // Inicializa o novo campo
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCodigoFilial() {
        return codigoFilial;
    }

    public void setCodigoFilial(String codigoFilial) {
        this.codigoFilial = codigoFilial;
    }

    public String getCnpj() { // Novo getter
        return cnpj;
    }

    public void setCnpj(String cnpj) { // Novo setter
        this.cnpj = cnpj;
    }
}