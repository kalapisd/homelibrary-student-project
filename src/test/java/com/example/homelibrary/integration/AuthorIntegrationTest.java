package com.example.homelibrary.integration;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.commands.AuthorCommand;
import com.example.homelibrary.DTO.commands.BookCommand;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.homelibrary.data.TestAuthorCommand.BACKMAN;
import static com.example.homelibrary.data.TestAuthorCommand.FINE;
import static com.example.homelibrary.data.TestAuthorCommand.GRECSO;
import static com.example.homelibrary.data.TestAuthorCommand.ROWLING;
import static com.example.homelibrary.data.TestBookCommand.AAT;
import static com.example.homelibrary.data.TestBookCommand.ELETED_UZLETE;
import static com.example.homelibrary.data.TestBookCommand.HARRY_POTTER;
import static com.example.homelibrary.data.TestBookCommand.TANCISKOLA;
import static com.example.homelibrary.data.TestBookCommand.VERA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String entityUrl;

    @BeforeEach
    void setUp() {
        String baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/authors";
    }

    @Test
    void emptyDatabase_getAll_shouldReturnEmptyList() {
        ResponseEntity<List<AuthorDTO>> response = restTemplate.exchange(entityUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<AuthorDTO> authors = response.getBody();
        assertEquals(Collections.emptyList(), authors);
    }


    @Test
    void emptyDatabase_addOne_shouldReturnAddedAuthor() {
        AuthorDTO savedAuthor = restTemplate.postForObject(entityUrl, BACKMAN, AuthorDTO.class);
        String expectedName = BACKMAN.getName();
        assertEquals(expectedName, savedAuthor.getName());
    }

    @Test
    void oneAuthorStored_getOneById_shouldReturnCorrectAuthor() {
        restTemplate.postForObject(entityUrl, BACKMAN, AuthorDTO.class);
        long id = 1L;
        final ResponseEntity<AuthorDTO> response = restTemplate.getForEntity(entityUrl + "/" + id, AuthorDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BACKMAN.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void oneAuthorStored_getOneByName_shouldReturnCorrectAuthor() {
        restTemplate.postForObject(entityUrl, BACKMAN, AuthorDTO.class);
        String name = BACKMAN.getName();
        final ResponseEntity<AuthorDTO> response = restTemplate.getForEntity(entityUrl + "/name/" + name, AuthorDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BACKMAN.getName(), Objects.requireNonNull(response.getBody()).getName());
    }

    @Test
    void someAuthorsStored_getAll_shouldReturnAll() {
        List<AuthorCommand> testData = List.of(BACKMAN, GRECSO, FINE, ROWLING);
        testData.forEach(author -> restTemplate.postForObject(entityUrl, author, AuthorDTO.class));

        AuthorDTO[] result = restTemplate.getForObject(entityUrl, AuthorDTO[].class);
        assertEquals(testData.size(), result.length);

        Set<String> names = testData.stream().map(AuthorCommand::getName).collect(Collectors.toSet());
        assertTrue(Arrays.stream(result).map(AuthorDTO::getName).allMatch(names::contains));
    }

    @Test
    void getBooksOfAuthor_shouldOnlyReturn_booksOfGivenAuthor() {
        String postURL = "http://localhost:" + port + "/books/manually";
        List<BookCommand> testData = List.of(HARRY_POTTER, TANCISKOLA, VERA, AAT);
        testData.forEach(book -> restTemplate.postForObject(postURL, book, BookDTO.class));
        String author = "Grecsó Krisztián";
        String booksURL = entityUrl + "/books?author=" + author;
        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(booksURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<BookDTO> books = response.getBody();
        assert books != null;

        List<String> authorNames = books.stream()
                .map(bookDTO -> bookDTO.getAuthors().get(0))
                .distinct()
                .toList();

        assertEquals(1, authorNames.size());
        assertEquals(author, authorNames.get(0));
    }

    @Test
    void oneAuthorStored_with_noBooks_deleteById_shouldDeleteAuthor() {
        restTemplate.postForObject(entityUrl, BACKMAN, AuthorDTO.class);
        restTemplate.delete(entityUrl + "/" + "1");

        final ResponseEntity<AuthorDTO> response = restTemplate.getForEntity(entityUrl + "/name/" + BACKMAN.getName(), AuthorDTO.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void oneAuthorStored_with_Books_deleteById_shouldNotDeleteAuthor() {
        String postURL = "http://localhost:" + port + "/books/manually";
        restTemplate.postForObject(postURL, ELETED_UZLETE, BookDTO.class);
        restTemplate.delete(entityUrl + "/" + "1");

        final ResponseEntity<AuthorDTO> response = restTemplate.getForEntity(entityUrl + "/name/" + BACKMAN.getName(), AuthorDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ELETED_UZLETE.getAuthors().get(0), response.getBody().getName());
    }
}
