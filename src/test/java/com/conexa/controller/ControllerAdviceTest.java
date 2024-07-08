package com.conexa.controller;

import com.conexa.dto.ErrorDTO;
import com.conexa.exception.AuthException;
import com.conexa.exception.ServerExternalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ControllerAdviceTest {

    @InjectMocks
    private ControllerAdvice controllerAdvice;

    @Test
    public void testServerExternalExceptionHandler() {
        ServerExternalException ex = new ServerExternalException("EXT-404", 404, "No se encontró el servidor externo");

        ResponseEntity<ErrorDTO> response = controllerAdvice.serverExternalExceptionHandler(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("EXT-404", response.getBody().getCode());
        assertEquals("No se encontró el servidor externo", response.getBody().getMessage());
    }

    @Test
    public void testRuntimeExceptionHandler() {
        // Given
        RuntimeException ex = new RuntimeException("Error interno");

        // When
        ResponseEntity<ErrorDTO> response = controllerAdvice.runtimeExceptionHandler(ex);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("C-500", response.getBody().getCode());
        assertEquals("Error interno", response.getBody().getMessage());
    }

    @Test
    public void testAuthExceptionHandler() {
        // Given
        AuthException ex = new AuthException("AUTH-403", 403, "Acceso no autorizado");

        // When
        ResponseEntity<ErrorDTO> response = controllerAdvice.authExceptionHandler(ex);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("AUTH-403", response.getBody().getCode());
        assertEquals("Acceso no autorizado", response.getBody().getMessage());
    }
}