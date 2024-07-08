package com.conexa.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PropertiesVehiclesDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;
import com.conexa.dto.VehiclesDTO;
import com.conexa.service.ConsumerService;
import com.conexa.service.VehicleService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VehicleServiceImpl implements VehicleService {

    private final ConsumerService consumerService;

    /**
     * Obtiene una lista paginada de todos los vehículos.
     *
     * @param uriVariables Un mapa de parámetros URI.
     * @return Un objeto PageDTO que contiene la lista paginada de VehiclesDTO.
     */
    @Override
    public PageDTO<VehiclesDTO> getAllVehicles(Map<String, String> uriVariables) {
        return this.consumerService.getExternalApiDataWithPages("/vehicles", uriVariables, VehiclesDTO.class);
    }

    /**
     * Obtiene los detalles de un vehículo específico por su ID.
     *
     * @param idVehicle El ID del vehículo a buscar.
     * @return Un objeto ResponseDetailDTO que contiene el ResultDetailDTO
     * con las propiedades del vehículo.
     */
    @Override
    public ResponseDetailDTO<ResultDetailDTO<PropertiesVehiclesDTO>> getVehiclesDetailById(String idVehicle) {
        return this.consumerService.getExternalApiData("/vehicles/" + idVehicle, null, ResponseDetailDTO.class);
    }

    /**
     * Obtiene los detalles de los vehículos filtrados por nombre.
     *
     * @param uriVariables Un mapa de parámetros URI.
     * @return Un objeto ResponseDetailDTO que contiene la lista de ResultDetailDTO
     * con las propiedades de los vehículos.
     */
    @Override
    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesVehiclesDTO>>> getVehiclesDetailByName(HashMap<String, String> uriVariables) {
        // TODO Auto-generated method stub
        return this.consumerService.getExternalApiData("/vehicles/", uriVariables, ResponseDetailDTO.class);

    }
}
