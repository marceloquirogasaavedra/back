package com.examen02.examen02.repository;

import com.examen02.examen02.model.Especialidad;
import com.examen02.examen02.model.Sucursal;
import com.examen02.examen02.model.Sucursal_Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Sucursal_EspecialidadRepository extends JpaRepository<Sucursal_Especialidad, Long> {

    // Buscar todas las especialidades de una sucursal específica
    List<Sucursal_Especialidad> findBySucursal(Sucursal sucursal);

    // Buscar todas las sucursales donde se encuentra una especialidad específica
    List<Sucursal_Especialidad> findByEspecialidad(Especialidad especialidad);

    // Método para verificar si una especialidad ya está asignada a una sucursal
    Optional<Sucursal_Especialidad> findBySucursalAndEspecialidad(Sucursal sucursal, Especialidad especialidad);

    // Consultar el número de especialidades asignadas a una sucursal
    @Query("SELECT COUNT(se) FROM Sucursal_Especialidad se WHERE se.sucursal = :sucursal")
    Long countBySucursal(@Param("sucursal") Sucursal sucursal);

    // Consultar el número de sucursales que tienen asignada una especialidad específica
    @Query("SELECT COUNT(se) FROM Sucursal_Especialidad se WHERE se.especialidad = :especialidad")
    Long countByEspecialidad(@Param("especialidad") Especialidad especialidad);

    // Eliminar una asignación específica de especialidad a sucursal
    void deleteBySucursalAndEspecialidad(Sucursal sucursal, Especialidad especialidad);
}


