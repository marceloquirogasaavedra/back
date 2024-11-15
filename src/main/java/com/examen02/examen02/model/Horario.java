package com.examen02.examen02.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "horario")
public class Horario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date hora_inicio;

    @Temporal(TemporalType.TIME)
    private Date hora_final;

    private Boolean estado;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_medico",nullable = false)
    private Medico id_medico;
}
