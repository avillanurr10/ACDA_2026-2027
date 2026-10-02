package com.centro.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String expediente;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    // Relación N:M con Modulo (Clase propietaria)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "alumnos_modulos",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "modulo_id")
    )
    private List<Modulo> modulos = new ArrayList<>();

    // Relación 1:1 Opcional: Un alumno tiene un Profesor Tutor
    @OneToOne
    @JoinColumn(name = "tutor_id", unique = true)
    private Profesor tutor;

    public Alumno() {}

    public Alumno(String nombre, String expediente, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.expediente = expediente;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Métodos de conveniencia para la relación N:M
    public void addModulo(Modulo modulo) {
        this.modulos.add(modulo);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getExpediente() { return expediente; }
    public void setExpediente(String expediente) { this.expediente = expediente; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public List<Modulo> getModulos() { return modulos; }
    public void setModulos(List<Modulo> modulos) { this.modulos = modulos; }
    public Profesor getTutor() { return tutor; }
    public void setTutor(Profesor tutor) { this.tutor = tutor; }
}