package com.examen02.examen02.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = "sucursal_especialidad")
public class Sucursal_Especialidad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // las tuplas ingresadas sean autoincrementales
    private Long id;


    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal id_sucursal;


    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad id_especialidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sucursal getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(Sucursal id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public Especialidad getId_especialidad() {
        return id_especialidad;
    }

    public void setId_especialidad(Especialidad id_especialidad) {
        this.id_especialidad = id_especialidad;
    }
}
