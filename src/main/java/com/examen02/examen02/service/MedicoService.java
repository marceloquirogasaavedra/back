package com.examen02.examen02.service;
import java.security.SecureRandom;
import ch.qos.logback.classic.pattern.LineSeparatorConverter;
import com.examen02.examen02.DTO.MedicoDTO;
import com.examen02.examen02.DTO.Sucursal_EspecialidadDTO;
import com.examen02.examen02.Security.PasswordGenerator;
import com.examen02.examen02.model.*;
import com.examen02.examen02.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class MedicoService {

    public List<Medico> findAll(){return medicoRepository.findAll();}

    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private  Sucursal_EspecialidadRepository sucursalEspecialidadRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private EspecialidadRepository especialidadRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private HorarioRepository horarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final SecureRandom random = new SecureRandom();


    @Transactional
    public Medico crearMedicoConUsuario(MedicoDTO medicoDTO, Sucursal_EspecialidadDTO sucursalEspecialidadDTO) {

        // Crear el Usuario
        Usuarios usuario = new Usuarios();
        usuario.setCorreo(medicoDTO.getEmail());
        String password = PasswordGenerator.generateRandomPassword(12);
        usuario.setPassword(passwordEncoder.encode(password));

        // Buscar el rol de médico y asignarlo al usuario
        Rol rolMedico = rolRepository.findByName("Medico");
        if (rolMedico == null) {
            throw new RuntimeException("Rol de Médico no encontrado");
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

        // Buscar la Sucursal y Especialidad
        Optional<Sucursal> sucursalOpt = sucursalRepository.findById(sucursalEspecialidadDTO.getId_sucursal());
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(sucursalEspecialidadDTO.getId_especialidad());

        if (sucursalOpt.isPresent() && especialidadOpt.isPresent()) {
            // Buscar la relación Sucursal_Especialidad existente en la base de datos
            Optional<Sucursal_Especialidad> existingRelation = sucursalEspecialidadRepository.findBySucursalAndEspecialidad(sucursalOpt.get(), especialidadOpt.get());

            if (existingRelation.isPresent()) {
                // Asigna la relación existente al médico
                medico.setId_sucursal_especialidad(existingRelation.get());
            } else {
                throw new RuntimeException("La relación entre Sucursal y Especialidad no existe.");
            }
        } else {
            throw new RuntimeException("Sucursal o Especialidad no encontrada");
        }

        // Guardar el medico en la base de datos
        Medico medicoCreado = medicoRepository.save(medico);

        // Crear los horarios automáticos para los días restantes de la semana actual
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int today = calendar.get(Calendar.DAY_OF_WEEK);
        List<Horario> horarios = new ArrayList<>();
        today=today+1;
        // Comenzar desde el día actual y solo agregar días hasta el viernes
        for (int i = today; i <= Calendar.FRIDAY; i++) {

            if (i >= Calendar.MONDAY && i <= Calendar.FRIDAY) { // Solo lunes a viernes
                // Establecer la fecha del horario
                Date fecha = calendar.getTime();

                // Crear nuevo horario para el día actual
                Horario horario = new Horario();
                horario.setFecha(fecha);

                // Configuración de la hora de inicio y fin
                Calendar horaInicio = (Calendar) calendar.clone();
                horaInicio.set(Calendar.HOUR_OF_DAY, 7);
                horaInicio.set(Calendar.MINUTE, 0);

                Calendar horaFin = (Calendar) calendar.clone();
                horaFin.set(Calendar.HOUR_OF_DAY, 16);
                horaFin.set(Calendar.MINUTE, 0);

                horario.setHora_inicio(horaInicio.getTime());
                horario.setHora_final(horaFin.getTime());
                horario.setEstado(true); // Activar el horario
                horario.setId_medico(medicoCreado);

                // Agregar el horario a la lista
                horarios.add(horario);
            }

            // Avanzar al siguiente día
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Guardar los horarios en la base de datos
        horarioRepository.saveAll(horarios);

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


    @Transactional
    public Medico modificarMedico(Long id, MedicoDTO medicoDTO, Sucursal_EspecialidadDTO sucursalEspecialidadDTO) {

        // Buscar el médico por ID
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        if (medicoOpt.isEmpty()) {
            throw new RuntimeException("Médico no encontrado");
        }

        Medico medico = medicoOpt.get();

        // Actualizar la información del médico
        medico.setNombre(medicoDTO.getNombre());
        medico.setApellidoPaterno(medicoDTO.getApellido_paterno());
        medico.setApellidoMaterno(medicoDTO.getApellido_materno());
        medico.setEstado(medicoDTO.getEstado());
        medico.setEmail(medicoDTO.getEmail());

        // Actualizar la relación de sucursal y especialidad si es necesario
        // Buscar la Sucursal y Especialidad
        Optional<Sucursal> sucursalOpt = sucursalRepository.findById(sucursalEspecialidadDTO.getId_sucursal());
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(sucursalEspecialidadDTO.getId_especialidad());

        if (sucursalOpt.isPresent() && especialidadOpt.isPresent()) {
            // Buscar la relación Sucursal_Especialidad existente en la base de datos
            Optional<Sucursal_Especialidad> existingRelation = sucursalEspecialidadRepository.findBySucursalAndEspecialidad(sucursalOpt.get(), especialidadOpt.get());

            if (existingRelation.isPresent()) {
                // Asigna la relación existente al médico
                medico.setId_sucursal_especialidad(existingRelation.get());
            } else {
                throw new RuntimeException("La relación entre Sucursal y Especialidad no existe.");
            }
        } else {
            throw new RuntimeException("Sucursal o Especialidad no encontrada");
        }

        // Guardar los cambios en la base de datos
        return medicoRepository.save(medico);
    }

}
