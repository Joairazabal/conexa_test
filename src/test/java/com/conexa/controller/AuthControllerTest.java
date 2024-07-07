package com.conexa.controller;

import com.conexa.security.jwt.JwtTokenUtil;
import com.conexa.security.payload.LoginRequestDTO;
import com.conexa.security.payload.RegisterRequestDTO;
import com.conexa.security.service.UserDetailsServiceImpl;
import com.conexa.service.AuthService;
import com.conexa.service.RoleService;
import com.conexa.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private UsersService usersService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private RoleService roleService;

    @Test
    public void testLogin() throws Exception {
//        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
//        loginRequestDTO.setEmail("test@example.com");
//        loginRequestDTO.setPassword("password");
//
//        Mockito.when(authService.login(Mockito.any(LoginRequestDTO.class)))
//                .thenReturn(ResponseEntity.ok("jwt-token"));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("jwt-token"));
    }

    @Test
    public void testRegister() throws Exception {
//        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
//        registerRequestDTO.setEmail("test@example.com");
//        registerRequestDTO.setPassword("password");
//
//        Mockito.when(authService.registrer(Mockito.any(RegisterRequestDTO.class)))
//                .thenReturn(ResponseEntity.ok("Usuario creado con éxito"));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Usuario creado con éxito"));
    }
}