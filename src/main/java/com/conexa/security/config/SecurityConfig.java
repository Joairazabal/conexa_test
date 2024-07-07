package com.conexa.security.config;

import com.conexa.exception.AuthException;
import com.conexa.security.jwt.JwtAuthEntryPoint;
import com.conexa.security.jwt.JwtRequestFilter;
import com.conexa.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuración de seguridad para la aplicación.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Autowired
    private PasswordEncoder encoder;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    /**
     * Configura el AuthenticationManager para autenticar usuarios usando
     * userDetailsService
     * y encoder para la codificación de contraseñas.
     *
     * @param http configuración de seguridad HTTP
     * @return AuthenticationManager configurado
     * @throws AuthException si hay un error durante la configuración
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) {
        try {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(encoder)
                    .and()
                    .build();
        } catch (Exception e) {
            throw new AuthException("AUTH-500", 500, e.getMessage());
        }

    }

    /**
     * Configuración del filtro de seguridad.
     * 
     * @param http Objeto HttpSecurity para configurar la seguridad HTTP.
     * @return SecurityFilterChain configurado.
     * @throws AuthException Si hay errores en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http
                    .cors(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .exceptionHandling(
                            exceptionHandling -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
                    .sessionManagement(
                            sessionManagement -> sessionManagement
                                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> authorize
                            .antMatchers("/api/v1/admin/**").hasAuthority("ADMIN")
                            .antMatchers("/api/auth/**").permitAll()
                            .anyRequest().permitAll());

            http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        } catch (Exception e) {
            throw new AuthException("AUTH-500", 500, e.getMessage());
        }

    }

    /**
     * Configura la fuente de configuración CORS para permitir solicitudes cruzadas
     * entre dominios.
     *
     * @return CorsConfigurationSource configurado.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "X-Requested-With", "Origin",
                "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}