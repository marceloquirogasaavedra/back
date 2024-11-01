package com.examen02.examen02.repository;

import com.examen02.examen02.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {
    Usuarios findByCorreo(String correo);
}
