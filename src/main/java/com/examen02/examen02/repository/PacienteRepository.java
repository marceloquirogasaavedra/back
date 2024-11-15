package com.examen02.examen02.repository;

import com.examen02.examen02.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    @Modifying
    @Query("UPDATE Paciente p SET p.estado = false")
    void deshabilitarTodosLosPacientes();

    Optional<Paciente> findByEmail(String email);

    Optional<Paciente> findByCi(String ci);
}
