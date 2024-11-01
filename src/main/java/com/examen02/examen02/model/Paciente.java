package com.examen02.examen02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "paciente")
public class Paciente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // las tuplas ingresadas sean autoincrementales
    private Long id;

    @Column(length = 50,nullable = false,unique = true)
    private String ci;
    @Column(length = 100,nullable = false)
    private String nombre;
    @Column(length = 100,nullable = false)
    private String apellido_paterno;
    @Column(length = 100,nullable = false)
    private String apellido_materno;
    @Column(nullable = false)
    private Date fecha_nacimiento;
    @Column(length = 50,nullable = false)
    private String nss;
    private String direccion;
    @Column(length = 8,nullable = false)
    private String telefono;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private Boolean estado;

    @OneToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuarios id_usuario;
}
