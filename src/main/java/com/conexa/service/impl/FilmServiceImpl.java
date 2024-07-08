package com.conexa.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.ResponseFilmDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.FilmService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {

    private final ConsumerService consumerService;


    /**
     * Obtiene una lista de películas desde una API externa basada en los parámetros proporcionados.
     *
     * @param uriVariables Mapa de variables de URI para filtrar la consulta a la API externa.
     * @return Objeto ResponseFilmDTO que contiene la respuesta de la API externa con las películas obtenidas.
     */
    @Override
    public ResponseFilmDTO getFilms(Map<String, String> uriVariables) {
        return this.consumerService.getExternalApiData("/films", uriVariables, ResponseFilmDTO.class);
    }

}
