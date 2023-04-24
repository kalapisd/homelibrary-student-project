package com.example.homelibrary.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {

    private String genre;
    private List<String> booksOfGenre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreDTO genreDTO)) return false;
        return Objects.equals(getGenre(), genreDTO.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenre());
    }
}
