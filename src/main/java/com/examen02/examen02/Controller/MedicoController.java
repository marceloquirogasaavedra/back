package com.examen02.examen02.Controller;


import com.examen02.examen02.DTO.MedicoConSucursalEspecialidadDTO;
import com.examen02.examen02.DTO.MedicoDTO;
import com.examen02.examen02.DTO.Sucursal_EspecialidadDTO;
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


    @PreAuthorize("hasAnyRole('Administrador')")
    @PostMapping(path = "/crear")
    public ResponseEntity<Medico> crearMedicoConUsuario(@RequestBody MedicoConSucursalEspecialidadDTO request) {
        Medico nuevoMedico = medicoService.crearMedicoConUsuario(request.getMedicoDTO(), request.getSucursalEspecialidadDTO());
        return new ResponseEntity<>(nuevoMedico, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('Administrador')")
    @GetMapping(path = "listar")
    public ResponseEntity<List<Medico>> listarMedicos(){
        return ResponseEntity.ok(medicoService.findAll());
    }
}
