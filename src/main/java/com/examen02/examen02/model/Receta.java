package com.examen02.examen02.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
@Entity
@Data
@Table(name = "receta")
public class Receta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private String receta;
    @ManyToOne
    @JoinColumn(name = "id_consulta",nullable = false)
    private Consulta id_consulta;
}
