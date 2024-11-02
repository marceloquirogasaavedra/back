package com.examen02.examen02.service;

import com.examen02.examen02.model.Rol;
import com.examen02.examen02.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.examen02.examen02.repository.UserRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar al usuario por nombre de usuario
        Usuarios userEntity = userRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        // Obtener el rol del usuario
        Rol userRole = userEntity.getId_rol(); // Suponiendo que cada usuario tiene un único rol

        // Convertir el rol en una colección de GrantedAuthority
        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));


        // Crear un objeto User de Spring Security
        return new User(userEntity.getCorreo(), userEntity.getPassword(), true, true, true, true, authorities);
    }
}
