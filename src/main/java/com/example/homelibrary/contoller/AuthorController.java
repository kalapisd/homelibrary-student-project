package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.commands.AuthorCommand;
import com.example.homelibrary.service.AuthorService;
import com.example.homelibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/findbyname/{name}")
    public ResponseEntity<AuthorDTO> findAuthorByName(@PathVariable("name") AuthorCommand command) {
        AuthorDTO authorDTO = authorService.findAuthorByName(command);
        if (authorDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(authorDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable("id") Long id) {
        AuthorDTO authorDTO = authorService.findAuthorById(id);
        if (authorDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(authorDTO);
        }
    }
    @PostMapping
    public  ResponseEntity<AuthorDTO> saveAuthor(@RequestBody @Valid AuthorCommand authorCommand, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authorService.saveAuthorByName(authorCommand));
    }

}
