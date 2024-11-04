package com.examen02.examen02.service;

import com.examen02.examen02.DTO.Sucursal_EspecialidadDTO;
import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.model.Sucursal;
import com.examen02.examen02.model.Sucursal_Especialidad;
import com.examen02.examen02.repository.EspecialidadRepository;
import com.examen02.examen02.repository.SucursalRepository;
import com.examen02.examen02.repository.Sucursal_EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Sucursal_EspecialidadService {

    @Autowired
    private Sucursal_EspecialidadRepository sucursalEspecialidadRepository;

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    // Servicio para asignar una especialidad a una sucursal
    public ResponseEntity<Sucursal_Especialidad> asignarEspecialidad(Sucursal_EspecialidadDTO sucursalEspecialidadDTO) {
        Optional<Sucursal> sucursalOpt = sucursalRepository.findById(sucursalEspecialidadDTO.getId_sucursal());
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(sucursalEspecialidadDTO.getId_especialidad());

        if (sucursalOpt.isPresent() && especialidadOpt.isPresent()) {
            Sucursal sucursal = sucursalOpt.get();
            Especialidad especialidad = especialidadOpt.get();

            // Verificar si ya existe la asignación
            Optional<Sucursal_Especialidad> existingRelation = sucursalEspecialidadRepository.findBySucursalAndEspecialidad(sucursal, especialidad);
            if (existingRelation.isPresent()) {
                // Si la relación ya existe, devuelve un código de conflicto
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            // Crear la nueva relación ya que no existe
            Sucursal_Especialidad sucursalEspecialidad = new Sucursal_Especialidad();
            sucursalEspecialidad.setSucursal(sucursal);
            sucursalEspecialidad.setEspecialidad(especialidad);

            Sucursal_Especialidad savedSucursalEspecialidad = sucursalEspecialidadRepository.save(sucursalEspecialidad);
            return new ResponseEntity<>(savedSucursalEspecialidad, HttpStatus.CREATED);
        } else {
            // Si no se encuentran la sucursal o la especialidad, devuelve un código de error 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Servicio para obtener todas las especialidades de una sucursal
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesDeSucursal(Long idSucursal) {
        Optional<Sucursal> sucursalOpt = sucursalRepository.findById(idSucursal);

        if (sucursalOpt.isPresent()) {
            List<Sucursal_Especialidad> relaciones = sucursalEspecialidadRepository.findBySucursal(sucursalOpt.get());
            List<Especialidad> especialidades = relaciones.stream()
                    .map(Sucursal_Especialidad::getId_especialidad)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(especialidades, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Servicio para obtener todas las sucursales donde está asignada una especialidad
    public ResponseEntity<List<Sucursal>> obtenerSucursalesDeEspecialidad(Long idEspecialidad) {
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(idEspecialidad);

        if (especialidadOpt.isPresent()) {
            List<Sucursal_Especialidad> relaciones = sucursalEspecialidadRepository.findByEspecialidad(especialidadOpt.get());
            List<Sucursal> sucursales = relaciones.stream()
                    .map(Sucursal_Especialidad::getId_sucursal)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(sucursales, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}