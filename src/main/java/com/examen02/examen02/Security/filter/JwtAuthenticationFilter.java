package com.examen02.examen02.Security.filter;

import com.examen02.examen02.Security.jwt.JwtUtils;
import com.examen02.examen02.model.Usuarios;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtUtils jwtUtils;

    private static final Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Intentando autenticar al usuario...");

        // Variables para almacenar el correo (usado como username) y la contraseña
        String email = "";
        String password = "";

        try {
            // Obtener el cuerpo de la solicitud e intentar convertirlo a un objeto Usuarios
            Usuarios userEntity = new ObjectMapper().readValue(request.getInputStream(), Usuarios.class);
            email = userEntity.getCorreo();  // Se usa el correo en lugar de username
            password = userEntity.getPassword();

            logger.info("Usuario obtenido: " + email);
        } catch (IOException e) {
            logger.severe("Error al leer la solicitud: " + e.getMessage());
            throw new RuntimeException("Error al procesar la autenticación", e);
        }

        // Crear el token de autenticación con el correo como username
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);

        // Intentar la autenticación con el AuthenticationManager
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        logger.info("Autenticación exitosa");

        // Obtener el usuario autenticado y su rol
        User user = (User) authResult.getPrincipal();
        String role = user.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"); // Usa un rol predeterminado si no se encuentra

        // Generar el token JWT usando el correo electrónico del usuario
        String token = jwtUtils.generateAccessToken(user.getUsername());  // Aquí user.getUsername() es el correo

        // Agregar el token en la cabecera de la respuesta
        response.addHeader("Authorization", "Bearer " + token);

        // Crear la respuesta en formato JSON incluyendo el rol
        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);
        httpResponse.put("message", "Autenticación correcta");
        httpResponse.put("email", user.getUsername());  // Devuelve el correo en la respuesta
        httpResponse.put("role", role);  // Agrega el rol en la respuesta

        // Configurar la respuesta HTTP
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        // Llamar al método de la clase padre para continuar con el flujo
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.warning("Autenticación fallida: " + failed.getMessage());

        // Devolver un mensaje de error en caso de autenticación fallida
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("message", "Autenticación fallida");

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.getWriter().flush();

        super.unsuccessfulAuthentication(request, response, failed);
    }
}