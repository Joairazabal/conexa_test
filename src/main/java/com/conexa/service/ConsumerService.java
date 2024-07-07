package com.conexa.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.conexa.dto.PageDTO;

@Service
public interface ConsumerService {

    public <T> PageDTO<T> getExternalApiDataWithPages(String url, Map<String, String> uriVariables, Class<T> itemType);

    public <T> T getExternalApiData(String url, Map<String, String> uriVariables,
            Class<T> responseType);

    public String formatUriVariables(Map<String, String> uriVariables, String url);

    public String extractDetailFromMessage(String message);
}
