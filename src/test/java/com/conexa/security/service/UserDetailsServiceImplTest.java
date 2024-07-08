package com.conexa.security.service;

import com.conexa.domain.Role;
import com.conexa.domain.Users;
import com.conexa.exception.AuthException;
import com.conexa.repository.RoleRepository;
import com.conexa.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {


    @Mock
    private UsersService usersService;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoadUserByUsername_Success() {
        // Arrange
        String email = "test@gmail.com";
        String password = "password";
        Role role = new Role("ROLE_USER");
        Users user = new Users(email, password, role);

        when(usersService.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));

        verify(usersService, times(1)).findByEmail(email);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String email = "noexist@gmail.com";

        when(usersService.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthException.class, () -> userDetailsService.loadUserByUsername(email));

        verify(usersService, times(1)).findByEmail(email);
    }

    @Test
    public void testSignUpUser() {
        // Arrange
        String email = "newuser@example.com";
        String password = "password";
        Role role = new Role("ROLE_USER");
        Users user = new Users(email, password, role);

        when(encoder.encode(password)).thenReturn("encodedPassword");
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        // Act
        userDetailsService.signUpUser(user);

        // Assert
        verify(encoder, times(1)).encode(password);
        verify(usersService, times(1)).save(any(Users.class));
    }
}