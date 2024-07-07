package com.conexa.service.impl;

import org.springframework.stereotype.Service;

import com.conexa.domain.Role;
import com.conexa.exception.AuthException;
import com.conexa.repository.RoleRepository;
import com.conexa.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Encuentra y devuelve el rol correspondiente al ID proporcionado.
     *
     * @param id El ID del rol a buscar.
     * @return El objeto Role encontrado.
     * @throws AuthException si no se encuentra el rol con el ID
     *                                especificado.
     */
    @Override
    public Role findById(Long id) {
        return this.roleRepository.findById(id)
                .orElseThrow(() -> new AuthException("AUTH-500", 500, "No se encontró el rol con el ID: " + id));
    }
}