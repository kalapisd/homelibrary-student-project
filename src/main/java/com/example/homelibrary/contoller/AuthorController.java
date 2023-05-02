package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.AuthorDTO;
import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.commands.AuthorCommand;
import com.example.homelibrary.service.AuthorService;
import com.example.homelibrary.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@Tag(name = "Here you can make operations on authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Find all authors",
            description = "Here you can get all authors stored in the database.")
    public List<AuthorDTO> getAuthors() {
        return authorService.findAllAuthors();
    }

    @GetMapping("/books")
    @Operation(summary = "Find all books of author",
            description = "Here you can get all books of an author of interest.")
    private List<BookDTO> getBooksOfAuthor(@RequestParam("author") String author) {
        return bookService.findAllBooksOfAuthor(author);
    }

    @GetMapping("/name/{author}")
    @Operation(summary = "Find author by name",
            description = "Here you can find an author by it's name.")
    public ResponseEntity<AuthorDTO> findAuthorByName(
            @Parameter(description = "Name of the author", example = "George Orwell")
            @PathVariable("author") String author) {
        AuthorDTO authorDTO = authorService.findAuthorByName(author);
        return authorDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(authorDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find author by id",
            description = "Here you can find an author by it's id.")
    public ResponseEntity<AuthorDTO> findAuthorById(
            @Parameter(description = "Id of the author", example = "1")
            @PathVariable("id") Long id) {
        AuthorDTO authorDTO = authorService.findAuthorById(id);
        return authorDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(authorDTO);
    }

    @PostMapping
    @Operation(summary = "Save an author",
            description = "Here you can save an author by giving it's name to the database.")
    public ResponseEntity<AuthorDTO> saveAuthor(
            @RequestBody @Valid AuthorCommand authorCommand, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(authorService.saveAuthorByName(authorCommand));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete author from database",
            description = "Here you can delete an author from the database by giving its ID You only can delete an author, if there is no book stored with this author.")
    public void delete(
            @Parameter(description = "Id of the author", example = "1")
            @PathVariable("id") Long id) {
        authorService.deleteAuthor(id);
    }
}
