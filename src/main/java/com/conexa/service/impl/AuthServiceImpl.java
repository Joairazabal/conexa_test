package com.conexa.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.conexa.domain.Role;
import com.conexa.domain.Users;
import com.conexa.exception.AuthException;
import com.conexa.security.jwt.JwtTokenUtil;
import com.conexa.security.payload.LoginRequestDTO;
import com.conexa.security.payload.RegisterRequestDTO;
import com.conexa.security.service.UserDetailsServiceImpl;
import com.conexa.service.AuthService;
import com.conexa.service.RoleService;
import com.conexa.service.UsersService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtUtil;

    private final UsersService usersService;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    private final RoleService roleService;

    private PasswordEncoder passwordEncoder;

    /**
     * Método para iniciar sesión de un usuario.
     *
     * @param requestUser DTO que contiene los datos de inicio de sesión del
     *                    usuario.
     * @return ResponseEntity con el token JWT generado después de la autenticación
     * exitosa.
     * @throws AuthException Si el usuario no se encuentra en la base de datos o la
     *                       contraseña proporcionada es incorrecta.
     */
    @Override
    public ResponseEntity<String> login(LoginRequestDTO requestUser) {

        Users user = this.usersService.findByEmail(requestUser.getEmail())
                .orElseThrow(() -> new AuthException("AUTH-404", 404, "Usuario no encontrado"));

        if (!passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
            throw new AuthException("AUTH-401", 401, "Contraseña incorrecta");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                requestUser.getEmail(), requestUser.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication, user.getRole().getName());

        return ResponseEntity.ok().body(jwt);
    }

    /**
     * Método para registrar un nuevo usuario.
     *
     * @param registerRequestDTO DTO que contiene los datos para registrar un nuevo
     *                           usuario.
     * @return ResponseEntity indicando que el usuario fue creado con éxito.
     * @throws AuthException Si ya existe un usuario con el mismo email en la base
     *                       de datos.
     */
    @Override
    public ResponseEntity<String> registrer(RegisterRequestDTO registerRequestDTO) {

        boolean userExists = this.usersService.existUserByEmail(registerRequestDTO.getEmail());

        if (userExists) {
            throw new AuthException("AUTH-406", 406, "Ya existe un usuario con ese email");
        }

        Role role = this.roleService.findById(1L);

        Users newUser = new Users();
        newUser.setEmail(registerRequestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));  // Codificar la contraseña
        newUser.setRole(role);

        this.userDetailsServiceImpl.signUpUser(newUser);

        return ResponseEntity.ok().body("Usuario creado con éxito");
    }

}