package com.test.conexa.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.conexa.ConexaApplication;

@SpringBootTest(classes = ConexaApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogin_Success() throws Exception {
        String loginRequestJson = "{\"username\": \"testuser\", \"password\": \"testpassword\"}";

        mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in successfully"));
    }

    @Test
    void testRegister_Success() throws Exception {
        String registerRequestJson = "{\"username\": \"testuser\", \"password\": \"testpassword\"}";

        mockMvc.perform(post("/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Registered successfully"));
    }
}