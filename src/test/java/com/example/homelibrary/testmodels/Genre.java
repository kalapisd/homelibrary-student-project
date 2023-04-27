package com.example.homelibrary.testmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private Long id;
    private GenreType genre;
    private Set<Book> booksOfGenre = new HashSet<>();

}
