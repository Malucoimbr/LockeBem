package com.code.fullstack_backend.model;
public class Filial {

    private Integer id;
    private String nome;
    private String cidade;
    private String estado;
    private String rua;
    private String numero;
    private String telefone;
    private String cnpj; // Novo campo


    public Filial() {}


    public Filial(Integer id, String nome, String cidade, String estado, String rua, String numero, String telefone,  String cnpj) {

        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.estado = estado;
        this.rua = rua;
        this.numero = numero;
        this.telefone = telefone;
        this.cnpj = cnpj;
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



    public String getCnpj() { // Novo getter
        return cnpj;
    }

    public void setCnpj(String cnpj) { // Novo setter
        this.cnpj = cnpj;
    }


}