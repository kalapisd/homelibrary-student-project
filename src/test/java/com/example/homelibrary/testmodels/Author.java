package com.example.homelibrary.testmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Author {
    private Long id;
    private String name;
    private Set<Book> books = new HashSet<>();

}
