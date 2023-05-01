package com.example.homelibrary.unit;

import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.GenreDTO;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.entity.GenreType;
import com.example.homelibrary.mapper.GenreMapper;
import com.example.homelibrary.repository.GenreRepository;
import com.example.homelibrary.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @InjectMocks
    GenreService service;

    @Mock
    GenreRepository genreRepository;

    @Mock
    GenreMapper mapper;

    @Test
    void should_find_and_return_all_genre() {

        setUpDatabaseForFindAll();

        List<GenreDTO> expectedDTOs = List.of(
                new GenreDTO("KIDS", List.of(new BookDTO("Harry Potter", List.of("Joanne K. Rowling"), "KIDS"))),
                new GenreDTO("SCI_FI", List.of(new BookDTO("Düne", List.of("Frank Herbert"), "SCI_FI")))
        );

        List<GenreDTO> actualDTOs = service.findAllGenre();

        assertTrue(expectedDTOs.size() == actualDTOs.size() && expectedDTOs.containsAll(actualDTOs) && actualDTOs.containsAll(expectedDTOs));

        verify(genreRepository, times(1)).findAll();
        verifyNoMoreInteractions(genreRepository);
    }

    @Test
    void should_find_and_return_genre_by_id() {

        Book book = Book.builder()
                .id(1L)
                .title("Harry Potter és a Bölcsek Köve")
                .subTitle(null)
                .authors(new HashSet<>())
                .genre(null)
                .publishedYear(2000)
                .numOfPages(316)
                .piece(1)
                .build();

        Genre genre = new Genre(1L, GenreType.KIDS, null);

        book.setGenre(genre);
        genre.setBooksOfGenre(new HashSet<>(List.of(book)));

        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genre));
        GenreDTO expectedDTO = new GenreDTO("KIDS", List.of(new BookDTO("Harry Potter", List.of("Joanne K. Rowling"), "KIDS")));
        when(mapper.toDTO(any())).thenReturn(expectedDTO);
        GenreDTO actualDTO = service.findGenreById(1L);
        assertEquals(expectedDTO, actualDTO);

        verify(genreRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(genreRepository);
    }

    @Test
    void should_not_find_genre_by_id_that_doesnt_exists() {
        when(genreRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(service.findGenreById(100L));

        verify(genreRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(genreRepository);

    }

    @Test
    void should_find_and_return_genreDTO_by_type() {

        Book book = Book.builder()
                .id(1L)
                .title("Harry Potter és a Bölcsek Köve")
                .subTitle(null)
                .authors(new HashSet<>())
                .genre(null)
                .publishedYear(2000)
                .numOfPages(316)
                .piece(1)
                .build();

        Genre genre = new Genre(1L, GenreType.KIDS, null);

        book.setGenre(genre);
        genre.setBooksOfGenre(new HashSet<>(List.of(book)));

        when(genreRepository.findByGenreType(any())).thenReturn(Optional.of(genre));
        GenreDTO expectedDTO = new GenreDTO("KIDS", List.of(new BookDTO("Harry Potter", List.of("Joanne K. Rowling"), "KIDS")));
        when(mapper.toDTO(any())).thenReturn(expectedDTO);
        GenreDTO actualDTO = service.findGenreByGenreType("KIDS");
        assertEquals(expectedDTO, actualDTO);

        verify(genreRepository, times(1)).findByGenreType(any());
        verifyNoMoreInteractions(genreRepository);
    }

    private void setUpDatabaseForFindAll() {
        Genre genre1 = new Genre(1L, GenreType.KIDS, null);
        Genre genre2 = new Genre(1L, GenreType.SCI_FI, null);

        GenreDTO genreDTO1 = new GenreDTO("KIDS", List.of(new BookDTO("Harry Potter", List.of("Joanne K. Rowling"), "KIDS")));
        GenreDTO genreDTO2 = new GenreDTO("SCI_FI", List.of(new BookDTO("Düne", List.of("Frank Herbert"), "SCI_FI")));

        when(genreRepository.findAll()).thenReturn(List.of(genre1, genre2));
        when(mapper.toDTO(genre1)).thenReturn(genreDTO1);
        when(mapper.toDTO(genre2)).thenReturn(genreDTO2);
    }
}
