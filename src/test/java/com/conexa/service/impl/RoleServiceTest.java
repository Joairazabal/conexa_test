package com.conexa.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conexa.domain.Role;
import com.conexa.exception.AuthException;
import com.conexa.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void findById_Success() {
        Long roleId = 1L;
        Role role = new Role();
        role.setId(roleId);
        role.setName("ROLE_USER");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Role result = roleService.findById(roleId);

        assertEquals(roleId, result.getId());
        assertEquals("ROLE_USER", result.getName());
    }

    @Test
    public void findById_RoleNotFound() {
        Long roleId = 2L;

        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        AuthException exception = assertThrows(AuthException.class, () -> {
            roleService.findById(roleId);
        });

        assertEquals("No se encontró el rol con el ID: " + roleId, exception.getMessage());
    }

}
