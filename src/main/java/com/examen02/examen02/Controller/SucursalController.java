package com.examen02.examen02.Controller;


import com.examen02.examen02.DTO.SucursalDTO;
import com.examen02.examen02.model.Sucursal;
import com.examen02.examen02.service.SucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sucursal")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    // Endpoint para listar todas las sucursales
    @PreAuthorize("hasAnyRole('Administrador','Medico','Paciente')")
    @GetMapping("/listar")
    public ResponseEntity<List<Sucursal>> getAllSucursales() {
        return sucursalService.findAll();
    }

    // Endpoint para guardar una nueva sucursal
    @PreAuthorize("hasRole('Administrador')")
    @PostMapping("/guardar")
    public ResponseEntity<Sucursal> createSucursal(@RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.save(sucursalDTO);
    }

    // Endpoint para modificar una sucursal existente
    @PreAuthorize("hasRole('Administrador')")
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Sucursal> updateSucursal(@PathVariable Long id, @RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.update(id, sucursalDTO);
    }

    // Endpoint para eliminar una sucursal
    @PreAuthorize("hasRole('Administrador')")
    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> deleteSucursal(@RequestBody SucursalDTO sucursalDTO) {
        return sucursalService.delete(sucursalDTO);
    }


}