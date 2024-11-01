package com.examen02.examen02.repository;
import com.examen02.examen02.model.Usuarios;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<Usuarios, Long>{

    Optional<Usuarios> findByCorreo(String correo);
}
