package com.examen02.examen02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "medico")
public class Medico  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellido_paterno", length = 100, nullable = false)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100, nullable = false)
    private String apellidoMaterno;

    @Column(name = "estado")
    private Boolean estado;
    @Email
    @Column(name = "email", length = 255, nullable = false,unique = true)
    private String email;


    @ManyToOne
    @JoinColumn(name = "id_usuario",nullable = true)
    private Usuarios id_usuario;


    @ManyToOne
    @JoinColumn(name = "id_sucursal_especialidad", nullable = true)
    private Sucursal_Especialidad id_sucursal_especialidad;

}
