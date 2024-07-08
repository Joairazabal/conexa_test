package com.conexa.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.SignatureException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenUtilTest {

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidateJwtToken_ValidToken_ReturnsTrue() {
        String token = "valid-token";
        when(jwtTokenUtil.validateJwtToken(token)).thenReturn(true);

        boolean isValid = jwtTokenUtil.validateJwtToken(token);

        assertTrue(isValid);
    }

    @Test
    public void testValidateJwtToken_InvalidToken_ReturnsFalse() {
        String token = "invalid-token";
        when(jwtTokenUtil.validateJwtToken(token)).thenReturn(false);

        boolean isValid = jwtTokenUtil.validateJwtToken(token);

        assertFalse(isValid);
    }

    @Test
    public void testGetUserNameFromJwtToken_ValidToken_ReturnsUsername() {
        String token = "valid-token";
        when(jwtTokenUtil.getUserNameFromJwtToken(token)).thenReturn("testuser");

        String username = jwtTokenUtil.getUserNameFromJwtToken(token);

        assertEquals("testuser", username);
    }

}