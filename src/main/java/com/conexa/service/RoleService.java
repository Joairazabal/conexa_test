package com.conexa.service;

import org.springframework.stereotype.Service;

import com.conexa.domain.Role;

@Service
public interface RoleService {

    Role findById(Long id);

}
