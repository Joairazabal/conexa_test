package com.conexa.service.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.conexa.dto.PageDTO;
import com.conexa.exception.ServerExternalException;
import com.conexa.service.ConsumerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Obtiene datos de una API externa y los devuelve como un objeto PageDTO.
 *
 * @param url          La URL del endpoint de la API externa.
 * @param uriVariables Un map que contiene las variables URI para la solicitud.
 * @param itemType     La clase de los elementos contenidos en el PageDTO.
 * @param <T>          El tipo de los elementos contenidos en el PageDTO.
 * @return Un objeto PageDTO que contiene los datos obtenidos de la API externa.
 * @throws ServerExternalException si ocurre un error al obtener datos de la API
 *                                 externa.
 */

@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private RestTemplate restTemplate;

    String BASE_URL = "https://www.swapi.tech/api";

    /**
     * Obtiene los datos paginados de la api externa
     *
     * @param uriVariables Un mapa que contiene las variables URI para la solicitud.
     * @param url          La URL de para obtener una respuesta de la rul base.
     * @param itemType     Clase con la cual debe mapear la data recibida por
     *                     RestTemplate.
     * @return Retorna una Page con un result generico.
     */
    @Override
    public <T> PageDTO<T> getExternalApiDataWithPages(String url, Map<String, String> uriVariables, Class<T> itemType) {
        try {
            ParameterizedTypeReference<PageDTO<T>> responseType = new ParameterizedTypeReference<PageDTO<T>>() {
            };

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Application");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            if (uriVariables != null && uriVariables.get("limit") == null) {
                uriVariables.put("limit", "10");
            }

            String fullUrl = this.formatUriVariables(uriVariables, BASE_URL + url);

            log.info(fullUrl);
            ResponseEntity<PageDTO<T>> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, responseType);

            return response.getBody();
        } catch (Exception e) {
            throw new ServerExternalException("EXT-500", 500, e.getMessage());
        }
    }

    @Override
    public <T> T getExternalApiData(String url, Map<String, String> uriVariables, Class<T> responseType) {
        try {
            String fullUrl = this.formatUriVariables(uriVariables, BASE_URL + url);

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Application");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            log.info("Formatted URL: " + fullUrl);
            ResponseEntity<T> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, responseType);

            return response.getBody();
        } catch (Exception e) {
            log.error(url, e);
            throw new ServerExternalException("EXT-500", 500, this.extractDetailFromMessage(e.getMessage()));
        }
    }

    /**
     * Formatea las variables URI y las agrega a la URL proporcionada.
     *
     * @param uriVariables Un mapa que contiene las variables URI para la solicitud.
     * @param url          La URL base a la que se agregarán las variables URI.
     * @return La URL completa con las variables URI formateadas y agregadas.
     */
    @Override
    public String formatUriVariables(Map<String, String> uriVariables, String url) {
        if (uriVariables != null && !uriVariables.isEmpty()) {
            url += "?" + uriVariables.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            return url;
        }
        return url;
    }

    /**
     * Extrae el detalle del mensaje de error de la exception que envia el servudor
     * externo
     *
     * @param message El mensaje que contiene una cadena JSON con detalles del
     *                error.
     * @return El detalle del error extraído de la cadena JSON, o el mensaje
     *         original si no se puede extraer el detalle.
     */
    @Override
    public String extractDetailFromMessage(String message) {
        try {
            // Use regex to find the JSON part
            Pattern pattern = Pattern.compile("\\{.*\\}");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                String json = matcher.group();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(json);
                return rootNode.path("detail").asText();
            }
        } catch (Exception ex) {
            // Log or handle the parsing exception if needed
        }
        return message; // Fallback to the original message if parsing fails
    }

}
