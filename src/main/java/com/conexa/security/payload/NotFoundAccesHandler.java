package com.conexa.security.payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class NotFoundAccesHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Puedes personalizar el código de estado y el cuerpo de la respuesta
        log.error("Acceso no autorizado: {}", accessDeniedException.getMessage());

        String errorMessage = "Acceso no autorizado: " + accessDeniedException.getMessage();
        log.error(errorMessage);

        // Prepara la respuesta JSON
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", "AUTH-403");
        responseBody.put("httpCode", HttpServletResponse.SC_FORBIDDEN); // Código HTTP 403
        responseBody.put("message", "No esta autenticado"); // Mensaje de error personalizado

        // Convierte la respuesta a JSON
        String jsonBody = objectMapper.writeValueAsString(responseBody);

        // Establece la respuesta HTTP
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Código HTTP 403
        response.getWriter().write(jsonBody);
    }


}
