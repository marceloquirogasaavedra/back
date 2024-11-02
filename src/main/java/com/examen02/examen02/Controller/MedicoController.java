package com.examen02.examen02.Controller;


import com.examen02.examen02.DTO.MedicoDTO;
import com.examen02.examen02.model.Medico;
import com.examen02.examen02.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    /**
     * Endpoint para crear un médico con usuario.
     * @param medicoDTO el DTO con los datos del médico
     * @return ResponseEntity con el médico creado o un error
     */
    @PreAuthorize("hasAnyRole('Administrador')")
    @PostMapping(path = "/crear")
    public ResponseEntity<?> crearMedico(@RequestBody MedicoDTO medicoDTO) {
        try {
            Medico medicoCreado = medicoService.crearMedicoConUsuario(medicoDTO);
            return new ResponseEntity<>(medicoCreado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el médico: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasAnyRole('Administrador')")
    @GetMapping(path = "listar")
    public ResponseEntity<List<Medico>> listarMedicos(){
        return ResponseEntity.ok(medicoService.findAll());
    }
}
