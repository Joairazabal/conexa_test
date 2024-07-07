package com.conexa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.conexa.dto.ErrorDTO;
import com.conexa.exception.AuthException;
import com.conexa.exception.ServerExternalException;

@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Maneja excepciones de tipo RuntimeException.
     *
     * @param ex La excepción de tipo RuntimeException lanzada.
     * @return ResponseEntity con un objeto ErrorDTO y código de estado HTTP 500
     *         (Internal Server Error).
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code("C-500")
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja excepciones de tipo ServerExternalException.
     *
     * @param ex La excepción de tipo ServerExternalException lanzada.
     * @return ResponseEntity con un objeto ErrorDTO y código de estado HTTP
     *         específico proporcionado por la excepción.
     */
    @ExceptionHandler(value = ServerExternalException.class)
    public ResponseEntity<ErrorDTO> serverExternalExceptionHandler(ServerExternalException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.valueOf(ex.getHttpCode()));
    }

    /**
     * Maneja excepciones de tipo AuthException.
     *
     * @param ex La excepción de tipo AuthException lanzada.
     * @return ResponseEntity con un objeto ErrorDTO y código de estado HTTP
     *         específico proporcionado por la excepción.
     */
    @ExceptionHandler(value = AuthException.class)
    public ResponseEntity<ErrorDTO> authExceptionHandler(AuthException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorDTO, HttpStatus.valueOf(ex.getHttpCode()));
    }
}