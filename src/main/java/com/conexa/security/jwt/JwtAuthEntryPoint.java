package com.conexa.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.conexa.exception.AuthException;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Punto de entrada para la autenticación JWT. Se utiliza para manejar las
     * excepciones de autenticación no autorizada.
     * Cuando un usuario intenta acceder a un recurso protegido sin autorización, se
     * lanza una excepción indicando la falta de permisos.
     *
     * @param httpServletRequest  Petición HTTP recibida.
     * @param httpServletResponse Respuesta HTTP que se enviará.
     * @param authException       de autenticación que desencadenó la
     *                            respuesta.
     * @throws IOException      Si ocurre un error de E/S al manejar la excepción.
     * @throws ServletException Si ocurre un error de servlet al manejar la
     *                          excepción.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException) throws IOException, ServletException {
        log.error("Acceso no autorizado: {}", authException.getMessage());

        String errorMessage = "Acceso no autorizado: " + authException.getMessage();
        log.error(errorMessage);

        // Prepara la respuesta JSON
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", "AUTH-403");
        responseBody.put("httpCode", HttpServletResponse.SC_FORBIDDEN); // Código HTTP 403
        responseBody.put("message", "No esta autenticado"); // Mensaje de error personalizado

        // Convierte la respuesta a JSON
        String jsonBody = objectMapper.writeValueAsString(responseBody);

        // Establece la respuesta HTTP
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN); // Código HTTP 403
        httpServletResponse.getWriter().write(jsonBody);
    }
}