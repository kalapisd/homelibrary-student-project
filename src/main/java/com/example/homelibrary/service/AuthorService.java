package com.example.homelibrary.service;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.command.AuthorCommand;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.mapper.AuthorMapper;
import com.example.homelibrary.repository.AuthorRepository;
import jakarta.transaction.Transactional;
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

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;
    private final AuthorMapper mapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper mapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    public AuthorDTO findAuthorByName(String name) {
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            return mapper.toDTO(author.get());
        } else {
            logger.info("No such author was found with name: {}!", name);
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
        return authorRepository.findAll().stream().map(mapper::toDTO).toList();
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

    public AuthorDTO saveAuthorByName(AuthorCommand command) {
        Author author = new Author(command.getName());
        return mapper.toDTO(authorRepository.save(author));
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Optional<Author> author = authorRepository.findById(id);

        int deletedRows = 0;
        if (author.isPresent() && author.get().getBooks().isEmpty()) {
            deletedRows = authorRepository.deleteAuthorById(id);
        }

        if (deletedRows == 0) {
            logger.info("Author not found with id: {} or cannot be deleted", id);
        } else {
            logger.info("Author with id: {} is deleted!", id);
        }
    }
}
