package com.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.conexa.domain.Role;
import com.conexa.domain.Users;
import com.conexa.exception.AuthException;
import com.conexa.security.jwt.JwtTokenUtil;
import com.conexa.security.payload.LoginRequestDTO;
import com.conexa.security.payload.RegisterRequestDTO;
import com.conexa.security.service.UserDetailsServiceImpl;
import com.conexa.service.RoleService;
import com.conexa.service.UsersService;
import com.conexa.service.impl.AuthServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtUtil;

    @Mock
    private UsersService usersService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    private Users mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockUser = new Users();
        mockUser.setEmail("test@gmail.com");
        mockUser.setPassword("password");

        Role role = new Role();
        role.setId(1L);
        role.setName("USER");
        mockUser.setRole(role);
    }

    // Pruebas unitarias

    @Test
    void testLoginSuccess() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("test@gmail.com", "password");

        when(usersService.findByEmail(eq("test@gmail.com"))).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(eq("password"), eq(mockUser.getPassword()))).thenReturn(true);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateJwtToken(eq(authentication), eq("USER"))).thenReturn("jwt-token");

        ResponseEntity<String> response = authServiceImpl.login(loginRequest);

        assertEquals(ResponseEntity.ok().body("jwt-token"), response);
    }

    @Test
    void testLoginUserNotFound() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("notfound@example.com", "password");

        when(usersService.findByEmail(eq("notfound@example.com"))).thenReturn(Optional.empty());

        AuthException exception = assertThrows(AuthException.class, () -> {
            authServiceImpl.login(loginRequest);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        assertEquals(404, exception.getHttpCode());
    }

    @Test
    void testLoginIncorrectPassword() {
        LoginRequestDTO loginRequest = new LoginRequestDTO("test@gmail.com", "wrong-password");

        when(usersService.findByEmail(eq("test@gmail.com"))).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(eq("wrong-password"), eq(mockUser.getPassword()))).thenReturn(false);

        AuthException exception = assertThrows(AuthException.class, () -> {
            authServiceImpl.login(loginRequest);
        });

        assertEquals("Contraseña incorrecta", exception.getMessage());
        assertEquals(401, exception.getHttpCode());
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("new@example.com", "password");

        when(usersService.existUserByEmail(eq("new@example.com"))).thenReturn(false);
        when(roleService.findById(eq(1L))).thenReturn(mockUser.getRole());

        ResponseEntity<String> response = authServiceImpl.registrer(registerRequest);

        verify(userDetailsService, times(1)).signUpUser(any(Users.class));
        assertEquals(ResponseEntity.ok().body("Usuario creado con éxito"), response);
    }

    @Test
    void testRegisterUserAlreadyExists() {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO("existing@example.com", "password");

        when(usersService.existUserByEmail(eq("existing@example.com"))).thenReturn(true);

        AuthException exception = assertThrows(AuthException.class, () -> {
            authServiceImpl.registrer(registerRequest);
        });

        assertEquals("Ya existe un usuario con ese email", exception.getMessage());
        assertEquals(406, exception.getHttpCode());
    }

}
