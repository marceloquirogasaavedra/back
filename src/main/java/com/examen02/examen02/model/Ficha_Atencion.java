package com.examen02.examen02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name="ficha_atencion")
public class Ficha_Atencion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ficha", nullable = false)
    private Date fechaFicha;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_solicitada", nullable = false)
    private Date fechaSolicitada;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente id_paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico id_medico;

}
