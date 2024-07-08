package com.conexa.security.service;

// import com.conexa.domain.ConfirmationToken;

import com.conexa.domain.Role;
import com.conexa.domain.Users;
import com.conexa.exception.AuthException;
import com.conexa.repository.RoleRepository;
// import com.conexa.service.RegistrationService;
import com.conexa.security.config.SecurityConfig;
import com.conexa.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleRepository roleRepository;


    /**
     * Carga un usuario por su nombre de usuario (en este caso, por email).
     *
     * @param email Email del usuario para buscar.
     * @return Detalles del usuario encontrado como UserDetails.
     * @throws AuthException Si el usuario no está presente en la base de datos.
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
     * Registra un nuevo usuario en el sistema.
     *
     * @param user Usuario a registrar.
     */
    public void signUpUser(Users user) {
        String encodedPassword = user.getPassword();
        user.setPassword(encodedPassword);

        Role defaultRole = roleRepository.findById(1L).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRole(defaultRole);

        this.usersService.save(user);
    }
}