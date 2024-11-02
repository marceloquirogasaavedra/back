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
        String usuario="12h09icsos@gmail.com";
        String password="mquiroga@2023.";
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
                    .anyMatch(user -> usuario.equals(user.getCorreo()));

            if (!adminExists) {
                // Crear el usuario administrador
                Usuarios adminUser = new Usuarios();
                adminUser.setCorreo(usuario);
                adminUser.setPassword(passwordEncoder.encode(password)); // Encriptar la contraseña
                adminUser.setId_rol(adminRole);

                // Guardar el usuario en la base de datos
                usuarioService.save(adminUser);
                System.out.println("Usuario administrador creado:"+usuario+" "+password);
            } else {

                System.out.println("Usuario ya existe"+usuario+" "+password);
            }
        };
    }
    @Bean
    public CommandLineRunner Crearroles() {
        return args -> {
            // Verificar si existe el rol "Administrador"
            Rol adminRole = rolService.findAll().stream()
                    .filter(role -> "Administrador".equals(role.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        // Si no existe, creamos el rol "Administrador"
                        Rol newAdminRole = new Rol();
                        newAdminRole.setName("Administrador");
                        return rolService.save(newAdminRole);
                    });
            // Verificar si existe el rol "Medico"
            Rol medicoRole = rolService.findAll().stream()
                    .filter(role -> "Medico".equals(role.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        // Si no existe, creamos el rol "Medico"
                        Rol newMedicoRole = new Rol();
                        newMedicoRole.setName("Medico");
                        return rolService.save(newMedicoRole);
                    });
            // Verificar si existe el rol "Paciente"
            Rol pacienteRole = rolService.findAll().stream()
                    .filter(role -> "Paciente".equals(role.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        // Si no existe, creamos el rol "Paciente"
                        Rol newPacienteRole = new Rol();
                        newPacienteRole.setName("Paciente");
                        return rolService.save(newPacienteRole);
                    });

        };
    }
}
