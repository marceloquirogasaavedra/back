package com.examen02.examen02.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "historia_clinica")
public class Historia_Clinica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultima_actualizacion;

    @OneToOne
    @JoinColumn (name = "id_paciente",nullable = false)
    private Paciente id_paciente;

}
