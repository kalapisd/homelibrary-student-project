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
public class Book {

    private Long id;
    private String title;
    private String subTitle;
    private Set<Author> authors = new HashSet<>();
    private Genre genre;
    private int publishedYear;
    private int numOfPages;
    private String language;
    private int piece;

}
