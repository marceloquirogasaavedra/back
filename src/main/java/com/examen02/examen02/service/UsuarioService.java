package com.examen02.examen02.service;

import com.examen02.examen02.DTO.UsuarioCambioPasswordDTO;
import com.examen02.examen02.DTO.UsuarioDTO;
import com.examen02.examen02.model.Usuarios;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.examen02.examen02.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuarios save (Usuarios c){
        return usuarioRepository.save(c);
    }
    @Transactional
    public Usuarios modificarContraseña(UsuarioCambioPasswordDTO c) {
        // Buscar el usuario por correo
        Usuarios usuario = usuarioRepository.findByCorreo(c.getCorreo());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con el correo proporcionado");
        }

        // Enviar correo electrónico con las credenciales
        String subject = "Combio de Contraseña";
        String message = "Cambio de contraseña exitoso" + "!\n\n"
                + "Estas son tus credenciales de acceso:\n"
                + "Usuario: " + c.getCorreo() + "\n"
                + "Contraseña: " + c.getPassword() + "\n\n"
                + "Muchas Gracias por usar nuestros servicios";
        emailService.sendSimpleMessage(c.getCorreo(), subject, message);

        // Modificar y guardar la contraseña
        usuario.setPassword(passwordEncoder.encode(c.getPassword()));
        return usuarioRepository.save(usuario); // Guardar los cambios en la base de datos
    }
}
