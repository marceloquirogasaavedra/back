package com.examen02.examen02.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


@Entity
@Data
@Table(name = "especialidad")
public class Especialidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // las tuplas ingresadas sean autoincrementales
    private Long id;
    @Column(length = 100,unique = true,nullable = false)
    private String nombre;
    private String descipcion;
}
