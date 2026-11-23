package com.centro.model;

public class Moto {
    private int id;
    private String marca;
    private String modelo;
    private int cilindrada;
    private double precio;

    public Moto() {
    }

    // Constructor sin ID (para inserciones)
    public Moto(String marca, String modelo, int cilindrada, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.precio = precio;
    }

    // Constructor con ID (para lecturas desde BD)
    public Moto(int id, String marca, String modelo, int cilindrada, double precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Marca: %s | Modelo: %s | Cilindrada: %d cc | Precio: %.2f €",
                id, marca, modelo, cilindrada, precio);
    }
}