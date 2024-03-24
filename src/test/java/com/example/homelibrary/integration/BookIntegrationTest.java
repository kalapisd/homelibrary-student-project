package com.example.homelibrary.integration;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.command.BookCommand;
import com.example.homelibrary.entity.GenreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.homelibrary.data.TestApiCommand.APIQUERY;
import static com.example.homelibrary.data.TestApiCommand.APIQUERY2;
import static com.example.homelibrary.data.TestAuthorCommand.HARUKI;
import static com.example.homelibrary.data.TestBookCommand.AAT;
import static com.example.homelibrary.data.TestBookCommand.ELETED_UZLETE;
import static com.example.homelibrary.data.TestBookCommand.HARRY_POTTER;
import static com.example.homelibrary.data.TestBookCommand.TANCISKOLA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("/application.properties")
class BookIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String entityUrl;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        entityUrl = baseUrl + "/books";
    }

    @Test
    void emptyDatabase_getAll_shouldReturnEmptyList() {
        ResponseEntity<List<BookDTO>> response = restTemplate.exchange(entityUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<BookDTO> books = response.getBody();
        assertEquals(Collections.emptyList(), books);
    }


    @Test
    void oneBookStored_getOneById_shouldReturnCorrectBook() {
        long id = 1L;
        String postURL = entityUrl + "/manually";
        restTemplate.postForObject(postURL, HARRY_POTTER, BookDTO.class);
        final ResponseEntity<BookDTO> response = restTemplate.getForEntity(entityUrl + "/" + id, BookDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HARRY_POTTER.getTitle(), Objects.requireNonNull(response.getBody()).getTitle());
    }

    @Test
    void oneBookStored_getOneByTitle_shouldReturnCorrectBook() {
        String title = HARRY_POTTER.getTitle();
        String postURL = entityUrl + "/manually";
        restTemplate.postForObject(postURL, HARRY_POTTER, BookDTO.class);
        final ResponseEntity<BookDTO> response = restTemplate.getForEntity(entityUrl + "/title/" + title, BookDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HARRY_POTTER.getTitle(), Objects.requireNonNull(response.getBody()).getTitle());
    }

    @Test
    void emptyDatabase_addOne_from_API_shouldReturnAddedBook() {
        String postURL = entityUrl + "/fromAPI";
        BookDTO result = restTemplate.postForObject(postURL, APIQUERY, BookDTO.class);
        String expectedTitle = "Harry Potter és a Titkok Kamrája";
        assertEquals(expectedTitle, result.getTitle());
    }

    @Test
    void emptyDatabase_addOne_manually_shouldReturnAddedBook() {
        String postURL = entityUrl + "/manually";
        BookDTO result = restTemplate.postForObject(postURL, HARRY_POTTER, BookDTO.class);
        assertEquals(HARRY_POTTER.getTitle(), result.getTitle());
    }

    @Test
    void someBooksStored_getAll_shouldReturnAll() {
        String postURL = entityUrl + "/manually";

        List<BookCommand> testData = List.of(HARRY_POTTER, ELETED_UZLETE, TANCISKOLA, AAT);
        testData.forEach(book -> restTemplate.postForObject(postURL, book, BookDTO.class));

        BookDTO[] result = restTemplate.getForObject(entityUrl, BookDTO[].class);
        assertEquals(testData.size(), result.length);

        Set<String> titles = testData.stream().map(BookCommand::getTitle).collect(Collectors.toSet());
        assertTrue(Arrays.stream(result).map(BookDTO::getTitle).allMatch(titles::contains));
    }

    @Test
    void oneBookStored_deleteById_getAllShouldReturnEmptyList() {
        long idToDelete = 1L;
        String postURL = entityUrl + "/manually";
        restTemplate.postForObject(postURL, HARRY_POTTER, BookDTO.class);
        restTemplate.delete(entityUrl + "/" + idToDelete);
        BookDTO[] result = restTemplate.getForObject(entityUrl, BookDTO[].class);
        assertEquals(0, result.length);
    }

    @Test
    void someBooksStored_deleteOne_keepRemaining() {
        String postURL = entityUrl + "/manually";
        List<BookCommand> testData = List.of(HARRY_POTTER, ELETED_UZLETE, TANCISKOLA, AAT);
        Set<String> expectedBookTitles = testData.stream().skip(1L).map(BookCommand::getTitle).collect(Collectors.toSet());
        testData.forEach(book -> restTemplate.postForObject(postURL, book, BookDTO.class));

        long idToDelete = 1L;
        restTemplate.delete(entityUrl + "/" + idToDelete);

        Set<String> actualBookTitles = Arrays.stream(restTemplate.getForObject(entityUrl, BookDTO[].class))
                .map(BookDTO::getTitle)
                .collect(Collectors.toSet());

        assertTrue(expectedBookTitles.size() == actualBookTitles.size() && expectedBookTitles.containsAll(actualBookTitles) && actualBookTitles.containsAll(expectedBookTitles));
    }

    @Test
    void multipleCopiesOfBookStored_getPiece_returnCorrectNumber() {
        String postURL = entityUrl + "/manually";
        List<BookCommand> testData = List.of(HARRY_POTTER, HARRY_POTTER);
        testData.forEach(book -> restTemplate.postForObject(postURL, book, BookDTO.class));

        final ResponseEntity<Integer> response = restTemplate.getForEntity(entityUrl + "/copies/" + HARRY_POTTER.getTitle(), Integer.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody());
    }

    @Test
    void oneBookStored_updateById_returnUpdatedBook() {
        String postURL = entityUrl + "/manually";
        restTemplate.postForObject(postURL, HARRY_POTTER, BookDTO.class);

        BookCommand update = BookCommand.builder()
                .title("Harry Potter és az Azkabani Fogoly")
                .authors(List.of("Joanne K. Rowling"))
                .genre("KIDS")
                .publishedYear(2001)
                .numOfPages(316)
                .language("hu")
                .build();

        long idToUpdate = 1L;
        String updateURL = entityUrl + "/" + idToUpdate;
        ResponseEntity<BookDTO> updatedBook = updateBook(updateURL, update);
        assertEquals(update.getTitle(), Objects.requireNonNull(updatedBook.getBody()).getTitle());
    }

    @Test
    void addAuthorToBook_shouldAddAuthor() {
        String postURL = entityUrl + "/fromAPI";
        restTemplate.postForObject(postURL, APIQUERY2, BookDTO.class);
        String bookId = "1";

        String postAuthor = baseUrl + "/authors";
        restTemplate.postForObject(postAuthor, HARUKI, AuthorDTO.class);
        String name = HARUKI.getName();

        String addAuthorUrl = entityUrl + "/addauthor";
        addAuthorToBook(addAuthorUrl, bookId, name);

        final ResponseEntity<BookDTO> response = restTemplate.getForEntity(entityUrl + "/" + bookId, BookDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HARUKI.getName(), Objects.requireNonNull(response.getBody()).getAuthors().get(0));
    }

    @Test
    void addGenreToBook_shouldAddGenre() {
        String postURL = entityUrl + "/fromAPI";
        restTemplate.postForObject(postURL, APIQUERY2, BookDTO.class);
        String bookId = "1";

        String addGenreUrl = entityUrl + "/addgenre";
        addGenreToBook(addGenreUrl, bookId, GenreType.LITERATURE.name());

        final ResponseEntity<BookDTO> response = restTemplate.getForEntity(entityUrl + "/" + bookId, BookDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(GenreType.LITERATURE.name(), Objects.requireNonNull(response.getBody()).getGenre());
    }

    private void addAuthorToBook(String url, String bookId, String name) {
        HttpEntity<MultiValueMap<String, String>> httpRequest = saveAuthorHttpEntityWithMediatypeForm(bookId,name);
        restTemplate.postForEntity(url, httpRequest, AuthorDTO.class);
    }

    private HttpEntity<MultiValueMap<String, String>> saveAuthorHttpEntityWithMediatypeForm(String bookId, String name) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("bookId",bookId);
        map.add("name", name);
        return new HttpEntity<>(map, headers);
    }

    private void addGenreToBook(String url, String bookId, String genre) {
        HttpEntity<MultiValueMap<String, String>> httpRequest = saveGenreHttpEntityWithMediatypeForm(bookId,genre);
        restTemplate.postForEntity(url, httpRequest, AuthorDTO.class);
    }

    private HttpEntity<MultiValueMap<String, String>> saveGenreHttpEntityWithMediatypeForm(String bookId, String genre) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("bookId",bookId);
        map.add("genre", genre);
        return new HttpEntity<>(map, headers);
    }

    private ResponseEntity<BookDTO> updateBook(String url, BookCommand command) {
        final HttpEntity<BookCommand> httpEntity = updateBookHttpEntityWithMediatypeJson(command);
        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, BookDTO.class);

    }

    private HttpEntity<BookCommand> updateBookHttpEntityWithMediatypeJson(BookCommand command) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(command, headers);
    }
}
