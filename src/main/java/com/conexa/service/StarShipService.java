package com.conexa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PropertiesStarshipDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.dto.StarshipDTO;

@Service
public interface StarShipService {

    public PageDTO<StarshipDTO> getStarShip(Map<String, String> uriVariables);

    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesStarshipDTO>>> getDetailStarshipByName(
            HashMap<String, String> uriVariables);

    public ResponseDetailDTO<ResultDetailDTO<PropertiesStarshipDTO>> getDetailStarshipById(
            String idStarship);
}
