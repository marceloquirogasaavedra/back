package com.examen02.examen02.repository;

import com.examen02.examen02.model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad,Long> {
    Optional<Especialidad> findByNombre(String nombre);
}
