package com.code.fullstack_backend.model;

public class User {

    private Long id;
    private String name;
    private String email;
    private String neighborhood;
    private String city;
    private String street;
    private String rg;

    // Construtor padrão (sem argumentos)
    public User() {}

    // Construtor com todos os argumentos
    public User(Long id, String name, String email, String neighborhood, String city, String street, String rg) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.neighborhood = neighborhood;
        this.city = city;
        this.street = street;
        this.rg = rg;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }
}
