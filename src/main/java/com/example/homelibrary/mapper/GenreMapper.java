package com.example.homelibrary.mapper;

import com.example.homelibrary.DTO.GenreDTO;
import com.example.homelibrary.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    private final BookMapper bookMapper;

    public GenreMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public GenreDTO toDTO(Genre genre) {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenre(genre.getGenreType().toString());
        genreDTO.setBooksOfGenre(genre.getBooksOfGenre().stream()
                .map(bookMapper::toDTO).sorted()
                .toList());
        return genreDTO;
    }
}
