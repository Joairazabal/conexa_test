package com.conexa.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.conexa.domain.Users;
import com.conexa.repository.UsersRepository;
import com.conexa.service.UsersService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario a buscar.
     * @return Un Optional que puede contener un objeto Users si se encuentra el
     *         usuario, o vacío si no se encuentra.
     */
    @Override
    public Optional<Users> findByEmail(String email) {
        return this.usersRepository.findByEmail(email);
    }

    /**
     * Verifica si existe un usuario con la dirección de correo electrónico
     * especificada.
     *
     * @param email La dirección de correo electrónico del usuario a verificar.
     * @return true si existe un usuario con el correo electrónico especificado,
     *         false de lo contrario.
     */
    @Override
    public Boolean existUserByEmail(String email) {
        return this.usersRepository.existsByEmail(email);
    }

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param user El usuario a guardar o actualizar.
     */
    @Override
    public void save(Users user) {
        this.usersRepository.save(user);
    }

}
