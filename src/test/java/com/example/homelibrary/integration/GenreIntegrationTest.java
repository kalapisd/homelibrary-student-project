package com.example.homelibrary.integration;

import com.example.homelibrary.testmodels.GenreDTO;
import com.example.homelibrary.testmodels.GenreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/application-test.properties")
public class GenreIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String entityUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/genre";
    }

    @Test
    void databaseFilledWithSQL_getAll_shouldReturnAllGenre() {
        List<String> expectedGenres = List.of("KIDS", "LITERATURE", "SOCIAL_SCIENCE");

        ResponseEntity<List<GenreDTO>> response = restTemplate.exchange(entityUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<GenreDTO> genre = response.getBody();
        List<String> actualGenres = genre.stream().map(GenreDTO::getGenre).toList();
        assertTrue(expectedGenres.size() == actualGenres.size() && expectedGenres.containsAll(actualGenres) && actualGenres.containsAll(expectedGenres));
    }

    @Test
    void databaseFilledWithSQL_getOneById_shouldReturnCorrectGenre() {
        Long id = 1L;
        final ResponseEntity<GenreDTO> response = restTemplate.getForEntity(entityUrl + "/" + id, GenreDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.valueOf(GenreType.KIDS), Objects.requireNonNull(response.getBody()).getGenre());

    }

}
