package com.example.homelibrary.unit;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.DTO.commands.AuthorCommand;
import com.example.homelibrary.entity.Author;
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
public class AuthorServiceTest {

    @InjectMocks
    AuthorService service;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorMapper mapper;

    @Captor
    ArgumentCaptor<Author> authorArgumentCaptor;


    @Test
    void should_return_empty_list_when_no_authors_present() {

        when(authorRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(service.findAllAuthors()).hasSize(0);
        verify(authorRepository, times(1)).findAll();
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_find_and_return_all_authors() {

        setUpDatabaseForFindAll();

        List<AuthorDTO> expectedAuthorDTOs = List.of(
                new AuthorDTO("Joanne K. Rowling", null),
                new AuthorDTO("Stephen King", null)
        );

        List<AuthorDTO> actualAuthorDTOs = service.findAllAuthors();

        assertTrue(expectedAuthorDTOs.size() == actualAuthorDTOs.size() && expectedAuthorDTOs.containsAll(actualAuthorDTOs) && actualAuthorDTOs.containsAll(expectedAuthorDTOs));

        verify(authorRepository, times(1)).findAll();
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void should_find_and_return_author_by_name() {
        Author author = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>())
                .build();

        when(authorRepository.findAuthorByName(anyString())).thenReturn(Optional.of(author));
        AuthorDTO expectedDTO = new AuthorDTO("Joanne K. Rowling", null);
        when(mapper.toDTO(any())).thenReturn(expectedDTO);
        AuthorDTO actualDTO = service.findAuthorByName("Joanne K. Rowling");
        assertEquals(expectedDTO, actualDTO);

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
        Author author = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>())
                .build();

        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
        AuthorDTO expectedDTO = new AuthorDTO("Joanne K. Rowling", null);
        when(mapper.toDTO(any())).thenReturn(expectedDTO);
        AuthorDTO actualDTO = service.findAuthorById(1L);
        assertEquals(expectedDTO, actualDTO);

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
    void save_author_return_saved_author(){

        Author savedAuthor = new Author(1L, "Mikszáth Kálmán", new HashSet<>());
        when(authorRepository.save(any())).thenReturn(savedAuthor);

        assertEquals(savedAuthor,authorRepository.save(any()));
        verify(authorRepository, times(1)).save(any());
        verifyNoMoreInteractions(authorRepository);
    }

    @Test
    void save_generated_author_by_name(){
        Author expectedSavedAuthor = new Author(1L, "Mikszáth Kálmán", new HashSet<>());
        AuthorCommand command = new AuthorCommand("Mikszáth Kálmán");
        when(authorRepository.save(any())).thenReturn(expectedSavedAuthor);

        service.saveAuthorByName(command);
        verify(authorRepository).save(authorArgumentCaptor.capture());

        Author authorToSave = authorArgumentCaptor.getValue();

        assertEquals(expectedSavedAuthor.getName(),authorToSave.getName());
        verify(authorRepository, times(1)).save(any());
        verifyNoMoreInteractions(authorRepository);
    }

    private void setUpDatabaseForFindAll() {

        Author author1 = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>())
                .build();

        Author author2 = Author.builder()
                .id(2L)
                .name("Stephen King")
                .books(new HashSet<>())
                .build();

        AuthorDTO authorDTO1 = new AuthorDTO("Joanne K. Rowling", null);
        AuthorDTO authorDTO2 = new AuthorDTO("Stephen King", null);

        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));
        when(mapper.toDTO(author1)).thenReturn(authorDTO1);
        when(mapper.toDTO(author2)).thenReturn(authorDTO2);
    }
}
