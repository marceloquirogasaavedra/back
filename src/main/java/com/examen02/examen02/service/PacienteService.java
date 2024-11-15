package com.examen02.examen02.service;

import com.examen02.examen02.DTO.PacienteDTO;
import com.examen02.examen02.model.*;
import com.examen02.examen02.repository.Historia_ClinicaRepository;
import com.examen02.examen02.repository.PacienteRepository;
import com.examen02.examen02.repository.UsuarioRepository;
import com.examen02.examen02.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Historia_ClinicaRepository historiaClinicaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public ResponseEntity<List<Paciente>> findAll() {
        List<Paciente> pacientes= pacienteRepository.findAll();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }
    @Transactional
    public void actualizarPacientes(List<PacienteDTO> pacientesDTO) {
        // Desactivar todos los pacientes
        pacienteRepository.findAll().forEach(paciente -> {
            paciente.setEstado(false);
            pacienteRepository.save(paciente);
        });

        // Procesar cada paciente en la lista proporcionada
        for (PacienteDTO pacienteDTO : pacientesDTO) {
            Paciente paciente = pacienteRepository.findByCi(pacienteDTO.getCi()).orElse(null);

            if (paciente == null) {
                // Crear usuario y paciente si no existe
                paciente = new Paciente();
                Rol rolPaciente = rolRepository.findByName("Paciente");
                Usuarios usuario = new Usuarios();
                usuario.setCorreo(pacienteDTO.getEmail());
                usuario.setPassword(passwordEncoder.encode("initialPassword123"));
                usuario.setId_rol(rolPaciente);
                usuarioRepository.save(usuario);

                paciente.setCi(pacienteDTO.getCi());
                paciente.setNombre(pacienteDTO.getNombre());
                paciente.setApellido_paterno(pacienteDTO.getApellido_paterno());
                paciente.setApellido_materno(pacienteDTO.getApellido_materno());
                paciente.setFecha_nacimiento(pacienteDTO.getFecha_nacimiento());
                paciente.setNss(pacienteDTO.getNss());
                paciente.setDireccion(pacienteDTO.getDireccion());
                paciente.setTelefono(pacienteDTO.getTelefono());
                paciente.setEmail(pacienteDTO.getEmail());
                paciente.setId_usuario(usuario);
                paciente.setEstado(true);

                pacienteRepository.save(paciente);

                Historia_Clinica historiaClinica = new Historia_Clinica();
                historiaClinica.setId_paciente(paciente);
                // Establecer la fecha de creación como java.util.Date ajustando la zona horaria UTC-04:00
                ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("UTC-04:00"));
                Instant instant = zdt.toInstant();
                historiaClinica.setFecha_creacion(Date.from(instant));
                historiaClinica.setUltima_actualizacion(Date.from(instant));
                historiaClinicaRepository.save(historiaClinica);

                // Enviar correo electrónico con los detalles del usuario
                String subject = "Bienvenido a Nuestro Sistema de Salud";
                String message = String.format("Hola %s,\n\nTu cuenta ha sido creada exitosamente con el siguiente correo: %s\nTu contraseña inicial es: initialPassword123\nPor favor cambia tu contraseña después de iniciar sesión por primera vez.\n\nGracias por registrarte.", paciente.getNombre(), paciente.getEmail());
                emailService.sendSimpleMessage(paciente.getEmail(), subject, message);
            } else {
                // Actualizar el estado del paciente si ya existe
                paciente.setEstado(true);
                pacienteRepository.save(paciente);
            }
        }
    }
}

