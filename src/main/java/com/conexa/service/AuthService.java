package com.conexa.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.conexa.security.payload.LoginRequestDTO;
import com.conexa.security.payload.RegisterRequestDTO;

@Service
public interface AuthService {

    public ResponseEntity<String> login(LoginRequestDTO requestUser);

    public ResponseEntity<String> registrer(RegisterRequestDTO registerRequestDTO);

}
