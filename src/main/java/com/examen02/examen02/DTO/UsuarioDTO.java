package com.examen02.examen02.DTO;

import lombok.Data;

@Data
public class UsuarioDTO {
    private  String correo;
    private  String username;
    private String password;
    private Long id_rol;
}
