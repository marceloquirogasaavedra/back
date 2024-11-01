package com.examen02.examen02.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class Historia_ClinicaDTO {
    private Date fecha_creacion;
    private Date ultima_actualizacion;
    private Long id_paciente;
}
