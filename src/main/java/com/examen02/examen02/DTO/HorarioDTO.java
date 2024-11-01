package com.examen02.examen02.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class HorarioDTO {
    private Date fecha;
    private Date hora_inicio;
    private Date hora_fin;
    private Boolean estado;
    private Long id_medico;
}
