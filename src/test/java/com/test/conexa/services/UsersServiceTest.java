package com.test.conexa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conexa.domain.Users;
import com.conexa.repository.UsersRepository;
import com.conexa.service.impl.UsersServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersServiceImpl usersService;

    @Test
    public void findByEmail() {
        Users user = new Users();
        user.setEmail("conexa@example.com");

        when(usersRepository.findByEmail("conexa@example.com")).thenReturn(Optional.of(user));

        Optional<Users> result = usersService.findByEmail("conexa@example.com");

        assertTrue(result.isPresent());
        assertEquals("conexa@example.com", result.get().getEmail());
    }

    @Test
    public void existUserByEmail() {
        when(usersRepository.existsByEmail("conexa@example.com")).thenReturn(true);

        Boolean result = usersService.existUserByEmail("conexa@example.com");

        assertTrue(result);
    }

    @Test
    public void save() {
        Users user = new Users();
        user.setEmail("conexa@example.com");

        usersService.save(user);

        verify(usersRepository).save(user);
    }
}