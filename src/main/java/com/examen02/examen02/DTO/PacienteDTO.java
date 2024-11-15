package com.examen02.examen02.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PacienteDTO {
    private String ci;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Date fecha_nacimiento;
    private String nss;
    private String direccion;
    private String telefono;
    private String email;
    private Boolean estado;
}
