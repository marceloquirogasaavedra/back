package com.examen02.examen02.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "consulta")
public class Consulta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha_consulta;
    private String diagnostico;
    private String informe;
    private Boolean estado;
    @ManyToOne
    @JoinColumn(name = "id_historia_clinica",nullable = false)
    private Historia_Clinica id_historia_clinica;
    @OneToOne
    @JoinColumn(name = "id_ficha_atencion",nullable = false)
    private Ficha_Atencion id_ficha_atencion;
}
