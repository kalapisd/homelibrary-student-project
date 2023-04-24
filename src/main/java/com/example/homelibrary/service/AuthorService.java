package com.example.homelibrary.service;

import com.example.homelibrary.DTO.commands.AuthorCommand;
import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.mapper.AuthorMapper;
import com.example.homelibrary.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private AuthorMapper mapper;

    private Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper mapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    public AuthorDTO findAuthorByName(AuthorCommand command) {
        Optional<Author> author = authorRepository.findAuthorByName(command.getName());
        if (author.isPresent()) {
            return mapper.toDTO(author.get());
        } else {
            logger.info("No such author was found with name: {}!", command.getName());
            return null;
        }
    }

    public AuthorDTO findAuthorById(long id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return mapper.toDTO(author.get());
        } else {
            logger.info("No such author was found with id: {}!", id);
            return null;
        }
    }

    public Optional<Author> getAuthorByName(String name) {
        return authorRepository.findAuthorByName(name);
    }

    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream().map(author -> mapper.toDTO(author)).toList();
    }

    public Set<Author> getAuthorsToSaveBook(List<String> authorNames) {
        Set<Author> authorsOfBook = new HashSet<>();
        if (authorNames != null) {
            for (String name : authorNames) {
                Optional<Author> author = authorRepository.findAuthorByName(name);
                if (author.isPresent()) {
                    authorsOfBook.add(author.get());
                } else {
                    Author generatedAuthor = authorRepository.save(new Author(name));
                    authorsOfBook.add(generatedAuthor);
                }
            }
        }
        return authorsOfBook;
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void saveAuthorByName(AuthorCommand command) {
        Author author = new Author(command.getName());
        authorRepository.save(author);
    }

}
