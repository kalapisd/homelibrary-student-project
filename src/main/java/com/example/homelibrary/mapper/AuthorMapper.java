package com.example.homelibrary.mapper;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDTO toDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(author.getName());
        authorDTO.setBooks(author.getBooks().stream().map(Book::getTitle).toList());

        return authorDTO;
    }

}
