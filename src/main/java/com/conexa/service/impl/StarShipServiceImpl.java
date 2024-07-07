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

    @Override
    public PageDTO<StarshipDTO> getStarShip(Map<String, String> uriVariables) {
        if (uriVariables.get("limit") == null) {
            uriVariables.put("limit", "10");
        }
        return this.consumerService.getExternalApiDataWithPages("/starships", uriVariables, StarshipDTO.class);
    }

    @Override
    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesStarshipDTO>>> getDetailStarshipByName(
            HashMap<String, String> uriVariables) {
        return this.consumerService.getExternalApiData("/starships", uriVariables,
                ResponseDetailDTO.class);
    }

    @Override
    public ResponseDetailDTO<ResultDetailDTO<PropertiesStarshipDTO>> getDetailStarshipById(String idStarship) {
        return this.consumerService.getExternalApiData("/starships/" + idStarship, null,
                ResponseDetailDTO.class);
    }

}
