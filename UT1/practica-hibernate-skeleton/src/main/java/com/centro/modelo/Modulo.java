package com.centro.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "modulos")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private Integer horasSemanales;

    // Relación N:1 con Profesor (Dueño de la Foreign Key 'profesor_id')
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    public Modulo() {}

    public Modulo(String nombre, Integer horasSemanales) {
        this.nombre = nombre;
        this.horasSemanales = horasSemanales;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getHorasSemanales() { return horasSemanales; }
    public void setHorasSemanales(Integer horasSemanales) { this.horasSemanales = horasSemanales; }
    public Profesor getProfesor() { return profesor; }
    public void setProfesor(Profesor profesor) { this.profesor = profesor; }
}

