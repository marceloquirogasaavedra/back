package com.examen02.examen02.DTO;

import com.examen02.examen02.model.Prueba_Medica;
import lombok.Data;

@Data
public class Prueba_MedicaDTO  {
    private String nombre;
    private  String doc_prueba;
    private  Long id_consulta;
}
