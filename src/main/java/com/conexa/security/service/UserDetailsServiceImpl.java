package com.conexa.security.service;

// import com.conexa.domain.ConfirmationToken;
import com.conexa.domain.Role;
import com.conexa.domain.Users;
import com.conexa.exception.AuthException;
import com.conexa.repository.RoleRepository;
// import com.conexa.service.RegistrationService;
import com.conexa.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio UserDetails para la autenticación de usuarios.
 * Este servicio carga los detalles de un usuario basado en su email y gestiona
 * el registro de nuevos usuarios.
 *
 * @param email Email del usuario para cargar sus detalles durante la
 *              autenticación.
 * @return UserDetails con los detalles del usuario encontrado.
 * @throws AuthException Si el usuario no se encuentra en la base de datos
 *                       durante la autenticación.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Carga los detalles del usuario basado en el email para la autenticación.
     *
     * @param email Email del usuario para cargar sus detalles.
     * @return UserDetails con los detalles del usuario encontrado.
     * @throws AuthException Si el usuario no se encuentra en la base de datos
     *                       durante la autenticación.
     */
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<Users> user = this.usersService.findByEmail(email);

        if (!user.isPresent()) {
            throw new AuthException("AUTH-403", 403, "Usuario no encontrado");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.get().getRole().getName()));

        return new User(user.get().getEmail(), user.get().getPassword(), authorities);
    }

    /**
     * Registra un nuevo usuario en la base de datos, cifrando su contraseña antes
     * de almacenarla.
     *
     * @param user Usuario a registrar.
     */
    public void signUpUser(Users user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        List<Role> roles = roleRepository.findAll();
        user.setRole(roles.get(0));

        this.usersService.save(user);
    }
}
