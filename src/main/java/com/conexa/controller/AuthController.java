package com.conexa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conexa.domain.Users;
import com.conexa.exception.AuthException;
import com.conexa.security.payload.LoginRequestDTO;
import com.conexa.security.payload.RegisterRequestDTO;
import com.conexa.service.AuthService;
import com.conexa.service.UsersService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequest) {
        return this.authService.login(loginRequest);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequestDTO) throws AuthException {

        try {
            return this.authService.registrer(registerRequestDTO);
        } catch (AuthException e) {
            throw e;
        }

    }

}
