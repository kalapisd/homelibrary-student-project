package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.commands.AuthorCommand;
import com.example.homelibrary.service.AuthorService;
import com.example.homelibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;
    private BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    public List<AuthorDTO> getAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping("/books")
    private List<BookDTO> getBooksOfAuthor(@RequestParam("name") String name) {
        return bookService.findAllBooksOfAuthor(name);
    }

    @GetMapping("/{name}")
    public AuthorDTO findAuthorByName(@PathVariable("name") AuthorCommand command) {
        return authorService.findAuthorByName(command);
    }

    @GetMapping("/{id}")
    public AuthorDTO findAuthorById(@PathVariable("id") Long id) {
        return authorService.findAuthorById(id);
    }

    @PostMapping
    public void save(@RequestParam("name") @Valid AuthorCommand authorCommand) {
        authorService.saveAuthorByName(authorCommand);
    }

}
