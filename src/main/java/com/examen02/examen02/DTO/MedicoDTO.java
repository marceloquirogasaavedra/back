package com.examen02.examen02.DTO;

import lombok.Data;

@Data
public class MedicoDTO {
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private Boolean estado;
    private String email;
    private Long id_usuario;
    private Long id_sucursal_especialidad;
}
