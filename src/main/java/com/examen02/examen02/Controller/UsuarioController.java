package com.examen02.examen02.Controller;

import com.examen02.examen02.DTO.UsuarioCambioPasswordDTO;
import com.examen02.examen02.DTO.UsuarioDTO;


import com.examen02.examen02.model.Rol;
import com.examen02.examen02.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.examen02.examen02.service.RolService;
import com.examen02.examen02.service.UsuarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping ("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping ("/listar")
    public ResponseEntity<List<Usuarios>> read() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping(path = "/guardar")
    public ResponseEntity<Usuarios> crear(@RequestBody UsuarioDTO c){

        try {
            Rol rol = rolService.findById(c.getId_rol()).orElseThrow(() -> new Exception("Rol no encontrado"));

            Usuarios nuevoUsuario = new Usuarios();
            nuevoUsuario.setCorreo(c.getCorreo());
            nuevoUsuario.setPassword(passwordEncoder.encode(c.getPassword()));
            nuevoUsuario.setId_rol(rol);  // Asigna el rol encontrado

            Usuarios nuevo = usuarioService.save(nuevoUsuario);
            return ResponseEntity.created(new URI("/aulas/crear/" + nuevo.getId())).body(nuevo);


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping(path = "/cambiarcontraseña")
    public ResponseEntity<?> modificarContraseña(@RequestBody UsuarioCambioPasswordDTO usuarioDTO) {
        try {
            Usuarios usuarioModificado = usuarioService.modificarContraseña(usuarioDTO);


            return new ResponseEntity<>(usuarioModificado, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al modificar la contraseña: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
