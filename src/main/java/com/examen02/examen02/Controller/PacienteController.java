package com.examen02.examen02.Controller;

import com.examen02.examen02.DTO.PacienteDTO;
import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.model.Paciente;
import com.examen02.examen02.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/actualizar")
    public ResponseEntity<String> actualizarPacientes(@RequestBody List<PacienteDTO> pacientesDTO) {
        try {
            pacienteService.actualizarPacientes(pacientesDTO);
            return new ResponseEntity<>("Pacientes actualizados correctamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar pacientes: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyRole('Administrador','Medico')")
    @GetMapping("/listar")
    public ResponseEntity<List<Paciente>> getAllEspecialidades() {
        return pacienteService.findAll();
    }

}
