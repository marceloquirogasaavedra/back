package com.examen02.examen02.Security;

import lombok.RequiredArgsConstructor;
import com.examen02.examen02.model.Rol;
import com.examen02.examen02.model.Usuarios;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.examen02.examen02.service.RolService;
import com.examen02.examen02.service.UsuarioService;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UsuarioService usuarioService;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdminUser() {
        return args -> {
            // Verificar si existe el rol "ADMIN"
            Rol adminRole = rolService.findAll().stream()
                    .filter(role -> "Administrador".equals(role.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        // Si no existe, creamos el rol "ADMIN"
                        Rol newAdminRole = new Rol();
                        newAdminRole.setName("Administrador");
                        return rolService.save(newAdminRole);
                    });

            // Verificar si ya existe un usuario administrador
            boolean adminExists = usuarioService.findAll().stream()
                    .anyMatch(user -> "12h09icsos@gmail.com".equals(user.getCorreo()));

            if (!adminExists) {
                // Crear el usuario administrador
                Usuarios adminUser = new Usuarios();
                adminUser.setCorreo("12h09icsos@gmail.com");
                adminUser.setPassword(passwordEncoder.encode("mquiroga@2023.")); // Encriptar la contraseña
                adminUser.setId_rol(adminRole);

                // Guardar el usuario en la base de datos
                usuarioService.save(adminUser);
                System.out.println("Usuario administrador creado: 12h09icsos@gmail.com");
            } else {
                System.out.println("Usuario ya existe.");
            }
        };
    }
}
