package com.conexa.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.conexa.exception.AuthException;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Punto de entrada para la autenticación JWT. Se utiliza para manejar las
 * excepciones de autenticación no autorizada.
 * Cuando un usuario intenta acceder a un recurso protegido sin autorización, se
 * lanza una excepción indicando la falta de permisos.
 *
 * @param httpServletRequest  Petición HTTP recibida.
 * @param httpServletResponse Respuesta HTTP que se enviará.
 * @param e                   Excepción de autenticación que desencadenó la
 *                            respuesta.
 * @throws IOException      Si ocurre un error de E/S al manejar la excepción.
 * @throws ServletException Si ocurre un error de servlet al manejar la
 *                          excepción.
 */
@Component
@Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            AuthenticationException e) throws IOException, ServletException {
        log.error("Acceso no autorizado: {}", e.getMessage());
        throw new AuthException("AUTH-403", 403, "No tienes los permisos necesarios");
    }
}