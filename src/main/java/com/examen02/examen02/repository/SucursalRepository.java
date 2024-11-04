package com.examen02.examen02.repository;

import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucursalRepository extends JpaRepository<Sucursal,Long> {
    Optional<Sucursal> findByNombre(String nombre);
}
