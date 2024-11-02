package com.examen02.examen02.service;

import com.examen02.examen02.DTO.EspecialidadDTO;
import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    // Listar todas las especialidades
    public ResponseEntity<List<Especialidad>> findAll() {
        List<Especialidad> especialidades = especialidadRepository.findAll();
        return new ResponseEntity<>(especialidades, HttpStatus.OK);
    }

    // Guardar una nueva especialidad
    public ResponseEntity<Especialidad> save(EspecialidadDTO especialidadDTO) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(especialidadDTO.getNombre());
        especialidad.setDescipcion(especialidadDTO.getDescripcion());

        Especialidad savedEspecialidad = especialidadRepository.save(especialidad);
        return new ResponseEntity<>(savedEspecialidad, HttpStatus.CREATED);
    }

    // Modificar una especialidad existente
    public ResponseEntity<Especialidad> update(Long id, EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findById(id);

        if (optionalEspecialidad.isPresent()) {
            Especialidad especialidad = optionalEspecialidad.get();
            especialidad.setNombre(especialidadDTO.getNombre());
            especialidad.setDescipcion(especialidadDTO.getDescripcion()); // Asegúrate de que este campo esté correcto en la entidad
            Especialidad updatedEspecialidad = especialidadRepository.save(especialidad);
            return new ResponseEntity<>(updatedEspecialidad, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una especialidad
    public ResponseEntity<Void> delete(EspecialidadDTO especialidadDTO) {
        Optional<Especialidad> optionalEspecialidad = especialidadRepository.findByNombre(especialidadDTO.getNombre());

        if (optionalEspecialidad.isPresent()) {
            especialidadRepository.deleteById(optionalEspecialidad.get().getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

