package com.examen02.examen02.model;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "sucursal")
public class Sucursal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // las tuplas ingresadas sean autoincrementales
    private Long id;
    @Column(nullable = false,unique = true)
    private String nombre;
    @Column(nullable = false,length = 255)
    private String direccion;

}
