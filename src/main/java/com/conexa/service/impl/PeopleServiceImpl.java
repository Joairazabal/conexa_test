package com.conexa.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PeopleDTO;
import com.conexa.dto.PropertiesPeopleDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.PeopleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PeopleServiceImpl implements PeopleService {

    private final ConsumerService consumerService;

    /**
     * Obtiene lista de detalles de una persona específica basada en los parámetros
     * de URI
     * proporcionados.
     *
     * @param uriVariables Mapa de variables de URI que pueden incluir 'name' u
     *                     otros parámetros.
     * @return Objeto ResponseDetailDTO con el mensaje de respuesta y la lista de
     * detalles.
     */
    @Override
    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesPeopleDTO>>> getPeopleDetailByName(
            Map<String, String> uriVariables) {
        return this.consumerService.getExternalApiData("/people", uriVariables, ResponseDetailDTO.class);
    }

    /**
     * Obtiene una lista paginada de personas basada en los parámetros de URI
     * proporcionados.
     * Si no se especifica 'limit', se usa un valor predeterminado de 10.
     *
     * @param uriVariables Mapa de variables de URI que pueden incluir 'limit' y
     *                     otros parámetros.
     * @return Objeto PageDTO<PeopleDTO> que contiene la lista paginada de personas.
     */
    @Override
    public PageDTO<PeopleDTO> getAllPeople(Map<String, String> uriVariables) {
        return this.consumerService.getExternalApiDataWithPages("/people", uriVariables, PeopleDTO.class);
    }

    /**
     * Obtiene el detalle de una persona basandose en parametro idPeople.
     * Si no se especifica 'limit', se usa un valor predeterminado de 10.
     *
     * @return Objeto ResponseDetailDTO<ResultDetailDTO<PropertiesPeopleDTO>> que contiene los detalles de una persona.
     */
    @Override
    public ResponseDetailDTO<ResultDetailDTO<PropertiesPeopleDTO>> getPeopleDetailById(String idPeople) {
        return this.consumerService.getExternalApiData("/people/" + idPeople, null, ResponseDetailDTO.class);

    }

}