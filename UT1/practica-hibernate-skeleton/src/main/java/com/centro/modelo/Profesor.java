package com.centro.modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    // Relación 1:N con Módulo (Inversa / Espejo)
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Modulo> modulos = new ArrayList<>();

    public Profesor() {}

    public Profesor(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Método de conveniencia para mantener sincronizados ambos lados de la relación
    public void addModulo(Modulo modulo) {
        modulos.add(modulo);
        modulo.setProfesor(this);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Modulo> getModulos() { return modulos; }
    public void setModulos(List<Modulo> modulos) { this.modulos = modulos; }
}