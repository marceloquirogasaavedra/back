package com.examen02.examen02.repository;


import com.examen02.examen02.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByName(String nombre);

}
