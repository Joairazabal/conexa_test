package com.conexa.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;
import com.conexa.dto.PeopleDTO;
import com.conexa.dto.PropertiesPeopleDTO;
import com.conexa.dto.ResponseDetailDTO;
import com.conexa.dto.ResultDetailDTO;

@Service
public interface PeopleService {

    public PageDTO<PeopleDTO> getAllPeople(Map<String, String> uriVariables);

    public ResponseDetailDTO<List<ResultDetailDTO<PropertiesPeopleDTO>>> getPeopleDetailByName(
            Map<String, String> uriVariables);

    public ResponseDetailDTO<ResultDetailDTO<PropertiesPeopleDTO>> getPeopleDetailById(
            String idPeople);

}
