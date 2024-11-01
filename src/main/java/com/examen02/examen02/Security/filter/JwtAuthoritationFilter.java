package com.examen02.examen02.Security.filter;

import com.examen02.examen02.Security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.examen02.examen02.service.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthoritationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            // Extraer el token JWT
            String token = tokenHeader.substring(7);

            // Verificar si el token es válido
            if (jwtUtils.isTokenValid(token)) {
                // Extraer el correo o username desde el token
                String username = jwtUtils.getUsername(token);

                // Verificar que no haya ya un usuario autenticado en el contexto
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Cargar los detalles del usuario
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Crear el token de autenticación
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Establecer el usuario autenticado en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        // Continuar con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}