package com.examen02.examen02.service;
import java.security.SecureRandom;
import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import com.examen02.examen02.DTO.MedicoDTO;
import com.examen02.examen02.Security.PasswordGenerator;
import com.examen02.examen02.model.Medico;
import com.examen02.examen02.model.Rol;
import com.examen02.examen02.model.Sucursal_Especialidad;
import com.examen02.examen02.model.Usuarios;
import com.examen02.examen02.repository.MedicoRepository;
import com.examen02.examen02.repository.RolRepository;
import com.examen02.examen02.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class MedicoService {

    public List<Medico> findAll(){return medicoRepository.findAll();}

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final SecureRandom random = new SecureRandom();
    @Transactional
    public Medico crearMedicoConUsuario(@RequestBody MedicoDTO medicoDTO) {

        // Crear el Usuario
        Usuarios usuario = new Usuarios();
        usuario.setCorreo(medicoDTO.getEmail());
        String password = PasswordGenerator.generateRandomPassword(12);
        usuario.setPassword(passwordEncoder.encode(password));
//        usuario.setPassword( medicoDTO.getNombre()); // Password basado en el nombre

        // Buscar el rol de médico y asignarlo al usuario
        Rol rolMedico = rolRepository.findByName("medico"); // Se asume que el nombre del rol es "medico"
        if (rolMedico == null) {
            throw new RuntimeException("Rol de médico no encontrado");
        }
        usuario.setId_rol(rolMedico);

        // Guardar el usuario en la base de datos
        Usuarios usuarioGuardado = usuarioRepository.save(usuario);

        // Crear el Medico y asociar el usuario guardado
        Medico medico = new Medico();
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellidoPaterno(medicoDTO.getApellido_paterno());
        medico.setApellidoMaterno(medicoDTO.getApellido_materno());
        medico.setEstado(medicoDTO.getEstado());
        medico.setEmail(medicoDTO.getEmail());
        medico.setId_usuario(usuarioGuardado);

        // Asignar id_sucursal_especialidad si es necesario
        // medico.setId_sucursal_especialidad(sucursalEspecialidadRepository.findById(medicoDTO.getId_sucursal_especialidad()).orElseThrow());

        // Guardar el medico en la base de datos
        // Guardar el medico en la base de datos
        Medico medicoCreado = medicoRepository.save(medico);

        // Enviar correo electrónico con las credenciales
        String subject = "Credenciales de acceso";
        String message = "Bienvenido, " + medicoDTO.getNombre() + "!\n\n"
                + "Estas son tus credenciales de acceso:\n"
                + "Usuario: " + medicoDTO.getEmail() + "\n"
                + "Contraseña: " + password + "\n\n"
                + "Por favor, cambia tu contraseña después de iniciar sesión.";
        emailService.sendSimpleMessage(medicoDTO.getEmail(), subject, message);

        return medicoCreado;
    }
}
