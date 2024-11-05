package com.examen02.examen02.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sucursal getId_sucursal() {
        return sucursal;
    }

    public void setId_sucursal(Sucursal id_sucursal) {
        this.sucursal = id_sucursal;
    }

    public Especialidad getId_especialidad() {
        return especialidad;
    }

    public void setId_especialidad(Especialidad id_especialidad) {
        this.especialidad = id_especialidad;
    }
}
