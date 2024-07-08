package com.conexa.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.conexa.dto.PageDTO;
import com.conexa.exception.ServerExternalException;

@ExtendWith(MockitoExtension.class)
public class ConsumerServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ConsumerServiceImpl consumerService;

    private final String BASE_URL = "https://www.swapi.tech/api";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(consumerService, "BASE_URL", BASE_URL);
    }

    @Test
    public void testGetExternalApiDataWithPages_Success() {
        String url = "/people";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("page", "1");
        Class<String> itemType = String.class;

        PageDTO<String> mockPageDto = new PageDTO<>();
        mockPageDto.setMessage("Data retrieved successfully");
        mockPageDto.setTotal_records(20);
        mockPageDto.setTotal_pages(2);
        mockPageDto.setPrevious("/people?page=1");
        mockPageDto.setNext("/people?page=3");
        mockPageDto.setResults(Arrays.asList("Luke Skywalker", "Darth Vader"));

        ResponseEntity<PageDTO<String>> mockResponse = ResponseEntity.ok(mockPageDto);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(mockResponse);

        PageDTO<String> result = consumerService.getExternalApiDataWithPages(url, uriVariables, itemType);

        assertEquals(mockPageDto.getMessage(), result.getMessage());
        assertEquals(mockPageDto.getTotal_records(), result.getTotal_records());
        assertEquals(mockPageDto.getTotal_pages(), result.getTotal_pages());
        assertEquals(mockPageDto.getPrevious(), result.getPrevious());
        assertEquals(mockPageDto.getNext(), result.getNext());
        assertEquals(mockPageDto.getResults().size(), result.getResults().size());
        assertEquals(mockPageDto.getResults().get(0), result.getResults().get(0));
        assertEquals(mockPageDto.getResults().get(1), result.getResults().get(1));
    }

    @Test
    public void testGetExternalApiDataWithPages_Exception() {
        String url = "/people";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("page", "1");
        Class<String> itemType = String.class;

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException(
                        "Error en la solicitud HTTP"));

        ServerExternalException exception = assertThrows(ServerExternalException.class, () -> {
            consumerService.getExternalApiDataWithPages(url, uriVariables, itemType);
        });

        assertEquals("EXT-500", exception.getCode());
    }

    @Test
    public void testGetExternalApiData_Success() {
        String url = "/people/1";
        Map<String, String> uriVariables = new HashMap<>();
        Class<String> responseType = String.class;
        ResponseEntity<String> mockResponse = ResponseEntity.ok("Luke Skywalker");

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(responseType)))
                .thenReturn(mockResponse);

        String result = consumerService.getExternalApiData(url, uriVariables, responseType);

        assertEquals("Luke Skywalker", result);
    }

    @Test
    public void testGetExternalApiData_Exception() {
        String url = "/people/999";
        Map<String, String> uriVariables = new HashMap<>();
        Class<String> responseType = String.class;

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(responseType)))
                .thenThrow(new RuntimeException("Error en la solicitud HTTP"));

        ServerExternalException exception = assertThrows(ServerExternalException.class, () -> {
            consumerService.getExternalApiData(url, uriVariables, responseType);
        });

        assertEquals("EXT-500", exception.getCode());
    }

    @Test
    public void testFormatUriVariables() {
        ConsumerServiceImpl service = new ConsumerServiceImpl();
        String expectedUrl = "https://www.swapi.tech/api/people?name=Luke&page=1";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("page", "1");
        uriVariables.put("name", "Luke");

        String formattedUrl = service.formatUriVariables(uriVariables, BASE_URL + "/people");

        assertEquals(expectedUrl, formattedUrl);
    }

    @Test
    public void testExtractDetailFromMessage() {
        String errorMessage = "Error en la solicitud: {\"detail\":\"Error interno en el servidor\"}";

        String result = consumerService.extractDetailFromMessage(errorMessage);

        assertEquals("Error interno en el servidor", result);
    }
}
