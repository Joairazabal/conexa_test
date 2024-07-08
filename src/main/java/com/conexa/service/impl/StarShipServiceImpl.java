package com.conexa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PropertiesStarshipDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.dto.StarshipDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.StarShipService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StarShipServiceImpl implements StarShipService {

    private final ConsumerService consumerService;

    /**
     * Obtiene una lista paginada de StarShips.
     * Si no se proporciona el parámetro `limit` en el mapa `uriVariables`,
     * se establece un valor por defecto de "10".
     *
     * @param uriVariables Un mapa de parámetros URI.
     * @return Un objeto PageDTO que contiene la lista paginada de Starships.
     */
    @Override
    public PageDTO<StarshipDTO> getStarShip(Map<String, String> uriVariables) {
        if (uriVariables.get("limit") == null) {
            uriVariables.put("limit", "10");
        }
        return this.consumerService.getExternalApiDataWithPages("/starships", uriVariables, StarshipDTO.class);
    }

    /**
     * Obtiene los detalles de los StarShips filtrados por nombre.
     *
     * @param uriVariables Un mapa de parámetros URI.
     * @return Un objeto ResponseDetailDTO que contiene la lista de ResultDetailDTO
     * con las propiedades de los Starships.
     */
    @Override
    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesStarshipDTO>>> getDetailStarshipByName(
            HashMap<String, String> uriVariables) {
        return this.consumerService.getExternalApiData("/starships", uriVariables,
                ResponseDetailDTO.class);
    }

    /**
     * Obtiene los detalles de un StarShip específico por su ID.
     *
     * @param idStarship El ID del StarShip a buscar.
     * @return Un objeto ResponseDetailDTO que contiene el ResultDetailDTO
     * con las propiedades del StarShip.
     */
    @Override
    public ResponseDetailDTO<ResultDetailDTO<PropertiesStarshipDTO>> getDetailStarshipById(String idStarship) {
        return this.consumerService.getExternalApiData("/starships/" + idStarship, null,
                ResponseDetailDTO.class);
    }

}
