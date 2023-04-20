package com.example.homelibrary.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GenreDTO {

    private String genre;
    private List<String> booksOfGenre;
}
