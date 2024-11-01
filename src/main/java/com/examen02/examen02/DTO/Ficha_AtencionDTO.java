package com.examen02.examen02.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class Ficha_AtencionDTO {
    private Date fecha_ficha;
    private String estado;
    private Date fecha_solicitada;
    private Long id_paciente;
    private Long id_medico;
}
