package com.examen02.examen02.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "prueba_medica")
public class Prueba_Medica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(length = 50,nullable = false)
    private String nombre;
    private String doc_prueba;

    @ManyToOne
    @JoinColumn(name = "id_consulta",nullable = false)
    private Consulta id_consulta;
}
