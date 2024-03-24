package com.example.homelibrary.unit;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.command.AuthorCommand;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.entity.GenreType;
import com.example.homelibrary.mapper.AuthorMapper;
import com.example.homelibrary.repository.AuthorRepository;
import com.example.homelibrary.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    AuthorService service;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorMapper mapper;

    @Captor
    ArgumentCaptor<Author> authorArgumentCaptor;

    private static final AuthorDTO DTO_ROWLING = new AuthorDTO("Joanne K. Rowling", null);
    private static final AuthorDTO DTO_KING = new AuthorDTO("Stephen King", null);

    private static final Author J_K_ROWLING = Author.builder()
            .id(1L)
            .name("Joanne K. Rowling")
            .books(new HashSet<>())
            .build();

    @Test
    void should_return_empty_list_when_no_authors_present() {

        when(authorRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(service.findAllAuthors()).isEmpty();
        verify(authorRepository, times(1)).findAll();
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_find_and_return_all_authors() {

        setUpDatabaseForFindAll();

        List<AuthorDTO> expectedAuthorDTOs = List.of(
                DTO_ROWLING,
                DTO_KING
        );

        List<AuthorDTO> actualAuthorDTOs = service.findAllAuthors();

        assertTrue(expectedAuthorDTOs.size() == actualAuthorDTOs.size() && expectedAuthorDTOs.containsAll(actualAuthorDTOs) && actualAuthorDTOs.containsAll(expectedAuthorDTOs));

        verify(authorRepository, times(1)).findAll();
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_find_and_return_author_by_name() {

        when(authorRepository.findAuthorByName(anyString())).thenReturn(Optional.of(J_K_ROWLING));
        when(mapper.toDTO(any())).thenReturn(DTO_ROWLING);
        AuthorDTO actualDTO = service.findAuthorByName("Joanne K. Rowling");
        assertEquals(DTO_ROWLING, actualDTO);

        verify(authorRepository, times(1)).findAuthorByName(anyString());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_not_find_author_by_name_that_doesnt_exists() {
        when(authorRepository.findAuthorByName(anyString())).thenReturn(Optional.empty());
        assertNull(service.findAuthorByName("Mikszáth Kálmán"));

        verify(authorRepository, times(1)).findAuthorByName(anyString());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_find_and_return_author_by_id() {

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(J_K_ROWLING));
        when(mapper.toDTO(any())).thenReturn(DTO_ROWLING);
        AuthorDTO actualDTO = service.findAuthorById(1L);
        assertEquals(DTO_ROWLING, actualDTO);

        verify(authorRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_not_find_author_by_id_that_doesnt_exists() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(service.findAuthorById(1L));

        verify(authorRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_save_and_return_author() {
        Author nonExistingAuthor = new Author(null, "Mikszáth Kálmán", new HashSet<>());
        Author savedAuthor = new Author(1L, "Mikszáth Kálmán", new HashSet<>());

        when(authorRepository.save(nonExistingAuthor)).thenReturn(savedAuthor);

        Author returnedAfterSave = service.saveAuthor(nonExistingAuthor);

        verify(authorRepository, times(1)).save(any());
        verifyNoMoreInteractions(authorRepository);

        assertEquals(savedAuthor, returnedAfterSave);
    }

    @Test
    void given_list_of_names_then_save_if_needed_and_return_authors() {
        List<String> authorNames = List.of("Joanne K. Rowling", "Stephen King");
        when(authorRepository.findAuthorByName(anyString())).thenReturn(Optional.empty());
        service.getAuthorsToSaveBook(authorNames);

        verify(authorRepository, times(2)).save(authorArgumentCaptor.capture());
        List<Author> allAuthors = authorArgumentCaptor.getAllValues();
        assertEquals("Joanne K. Rowling", allAuthors.get(0)
                .getName());
        assertEquals("Stephen King", allAuthors.get(1)
                .getName());
    }

    @Test
    void save_author_return_saved_author() {

        Author savedAuthor = new Author(1L, "Mikszáth Kálmán", new HashSet<>());
        when(authorRepository.save(any())).thenReturn(savedAuthor);

        assertEquals(savedAuthor, authorRepository.save(any()));
        verify(authorRepository, times(1)).save(any());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void save_generated_author_by_name() {
        Author expectedSavedAuthor = new Author(1L, "Mikszáth Kálmán", new HashSet<>());
        AuthorCommand command = new AuthorCommand("Mikszáth Kálmán");
        when(authorRepository.save(any())).thenReturn(expectedSavedAuthor);

        service.saveAuthorByName(command);
        verify(authorRepository).save(authorArgumentCaptor.capture());

        Author authorToSave = authorArgumentCaptor.getValue();

        assertEquals(expectedSavedAuthor.getName(), authorToSave.getName());
        verify(authorRepository, times(1)).save(any());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void author_with_existing_books_should_not_be_deleted() {
        Author expectedSavedAuthor = buildOneAuthorWithBook();
        AuthorCommand command = new AuthorCommand(expectedSavedAuthor.getName());
        when(authorRepository.save(any())).thenReturn(expectedSavedAuthor);
        when(authorRepository.findById(any())).thenReturn(Optional.of(expectedSavedAuthor));
        service.saveAuthorByName(command);

        service.deleteAuthor(1L);

        verify(authorRepository, times(0)).deleteAuthorById(any());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void author_with_no_books_is_deleted_on_delete() {
        Author expectedSavedAuthor = new Author(1L, "Mikszáth Kálmán", new HashSet<>());
        AuthorCommand command = new AuthorCommand("Mikszáth Kálmán");
        when(authorRepository.save(any())).thenReturn(expectedSavedAuthor);
        when(authorRepository.findById(any())).thenReturn(Optional.of(expectedSavedAuthor));
        when(authorRepository.deleteAuthorById(any())).thenReturn(1);
        service.saveAuthorByName(command);

        service.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteAuthorById(any());
        verifyNoMoreInteractions(authorRepository);
    }

    private void setUpDatabaseForFindAll() {

        Author author2 = Author.builder()
                .id(2L)
                .name("Stephen King")
                .books(new HashSet<>())
                .build();

        when(authorRepository.findAll()).thenReturn(List.of(J_K_ROWLING, author2));
        when(mapper.toDTO(J_K_ROWLING)).thenReturn(DTO_ROWLING);
        when(mapper.toDTO(author2)).thenReturn(DTO_KING);
    }

    private Author buildOneAuthorWithBook() {
        Book book1 = Book.builder()
                .id(1L)
                .title("Harry Potter és a Bölcsek Köve")
                .subTitle(null)
                .authors(new HashSet<>())
                .genre(null)
                .publishedYear(2000)
                .numOfPages(316)
                .copies(1)
                .build();

        Author author = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>(List.of(book1)))
                .build();

        book1.setAuthors(new HashSet<>(List.of(author)));

        Genre kids = Genre.builder()
                .id(1L)
                .genreType(GenreType.KIDS)
                .booksOfGenre(new HashSet<>(List.of(book1)))
                .build();

        book1.setGenre(kids);
        kids.getBooksOfGenre().forEach(book -> book.setGenre(kids));

        return author;
    }
}
