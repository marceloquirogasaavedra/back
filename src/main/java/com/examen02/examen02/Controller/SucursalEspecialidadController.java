package com.examen02.examen02.controller;

import com.examen02.examen02.DTO.Sucursal_EspecialidadDTO;
import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.model.Sucursal;
import com.examen02.examen02.model.Sucursal_Especialidad;
import com.examen02.examen02.service.Sucursal_EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal_especialidad")
public class SucursalEspecialidadController {

    @Autowired
    private Sucursal_EspecialidadService sucursalEspecialidadService;

    // Endpoint para asignar una especialidad a una sucursal
    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/asignar")
    public ResponseEntity<Sucursal_Especialidad> asignarEspecialidad(@RequestBody Sucursal_EspecialidadDTO sucursalEspecialidadDTO) {
        return sucursalEspecialidadService.asignarEspecialidad(sucursalEspecialidadDTO);
    }

    @PreAuthorize("hasAnyRole('Administrador','Medico','Paciente')")
    // Endpoint para obtener todas las especialidades de una sucursal
    @GetMapping("/especialidades/{idSucursal}")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesDeSucursal(@PathVariable Long idSucursal) {
        return sucursalEspecialidadService.obtenerEspecialidadesDeSucursal(idSucursal);
    }

    // Endpoint para obtener todas las sucursales donde está asignada una especialidad
    @PreAuthorize("hasAnyRole('Administrador','Medico','Paciente')")
    @GetMapping("/sucursales/{idEspecialidad}")
    public ResponseEntity<List<Sucursal>> obtenerSucursalesDeEspecialidad(@PathVariable Long idEspecialidad) {
        return sucursalEspecialidadService.obtenerSucursalesDeEspecialidad(idEspecialidad);
    }
}

