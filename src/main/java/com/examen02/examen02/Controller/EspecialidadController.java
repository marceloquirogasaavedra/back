package com.examen02.examen02.Controller;


import com.examen02.examen02.DTO.EspecialidadDTO;
import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.repository.SucursalRepository;
import com.examen02.examen02.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/especialidad")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Endpoint para listar todas las especialidades
    @PreAuthorize("hasAnyRole('Administrador','Medico','Paciente')")
    @GetMapping("/listar")
    public ResponseEntity<List<Especialidad>> getAllEspecialidades() {
        return especialidadService.findAll();
    }

    // Endpoint para guardar una nueva especialidad
    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public ResponseEntity<Especialidad> createEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.save(especialidadDTO);
    }

    // Endpoint para modificar una especialidad existente
    @PreAuthorize("hasRole('Administrador')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Especialidad> updateEspecialidad(@PathVariable Long id, @RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.update(id, especialidadDTO);
    }

    // Endpoint para eliminar una especialidad
    @PreAuthorize("hasRole('Administrador')")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteEspecialidad(@RequestBody EspecialidadDTO especialidadDTO) {
        return especialidadService.delete(especialidadDTO);
    }

}