package com.conexa.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración para proporcionar un bean de PasswordEncoder utilizando
 * BCryptPasswordEncoder.
 * Este bean se utiliza para codificar y validar contraseñas de manera segura en
 * la aplicación.
 */
@Configuration
public class EncoderConfig {

    /**
     * Crea un bean de PasswordEncoder utilizando BCryptPasswordEncoder.
     *
     * @return BCryptPasswordEncoder instancia de BCryptPasswordEncoder para
     *         hashear la contraseña.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}