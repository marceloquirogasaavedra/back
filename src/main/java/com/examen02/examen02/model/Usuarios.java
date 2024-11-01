package com.examen02.examen02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;

@Entity //la clase es una entidad
@Data // alamacena y genera de manera automatica los getters y setters
@Table(name = "usuarios") // dar nombre específico a la tabla
public class Usuarios implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // las tuplas ingresadas sean autoincrementales
    private Long id;
    @Email
    @Column(unique = true)
    private String correo;
    private String password;

    @ManyToOne //relación de muchos a uno
    @JoinColumn(name = "id_rol",nullable = false)
    private Rol id_rol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getId_rol() {
        return id_rol;
    }

    public void setId_rol(Rol id_rol) {
        this.id_rol = id_rol;
    }
}