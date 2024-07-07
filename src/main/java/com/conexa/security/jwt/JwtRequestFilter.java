package com.conexa.security.jwt;

import com.conexa.exception.AuthException;
import com.conexa.security.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro de solicitud JWT para la autenticación basada en tokens JWT.
 * Este filtro intercepta cada solicitud entrante para validar y procesar el
 * token JWT proporcionado en la cabecera de autorización.
 * Si el token es válido, establece la autenticación del usuario en el contexto
 * de seguridad.
 *
 * @param request     Petición HTTP recibida.
 * @param response    Respuesta HTTP que se enviará.
 * @param filterChain Cadena de filtros para continuar el procesamiento de la
 *                    solicitud.
 * @throws ServletException Si ocurre un error de servlet al procesar la
 *                          solicitud.
 * @throws IOException      Si ocurre un error de E/S al procesar la solicitud.
 */
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    public static final String BEARER = "Bearer ";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Implementación del filtro interno para procesar la autenticación basada en
     * JWT.
     *
     * @param request     Petición HTTP recibida.
     * @param response    Respuesta HTTP que se enviará.
     * @param filterChain Cadena de filtros para continuar el procesamiento de la
     *                    solicitud.
     * @throws ServletException Si ocurre un error de servlet al procesar la
     *                          solicitud.
     * @throws IOException      Si ocurre un error de E/S al procesar la solicitud.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtTokenUtil.validateJwtToken(jwt)) {
                String username = jwtTokenUtil.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication. idRol: {}", e);
            throw new AuthException("AUTH-500", 500, e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT de la cabecera Authorization de la solicitud.
     *
     * @param request Petición HTTP recibida.
     * @return Token JWT extraído, o null si no se encuentra o no está en el formato
     *         esperado.
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER))
            return headerAuth.substring(BEARER.length());

        return null;
    }

}
