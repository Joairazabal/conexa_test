package com.conexa.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.conexa.domain.Users;

@Service
public interface UsersService {

    Optional<Users> findByEmail(String email);

    Boolean existUserByEmail(String email);

    void save(Users user);

    
}
