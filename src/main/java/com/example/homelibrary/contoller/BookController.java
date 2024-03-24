package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.command.APICommand;
import com.example.homelibrary.command.BookCommand;
import com.example.homelibrary.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:3000")
@RequestMapping("/books")
@Tag(name = "Here you can make operations on books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testAuth() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "Find all books", description = "Here you can get all books stored in the database.")
    public List<BookDTO> getBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/best")
    @Operation(summary = "Find all books", description = "Here you can get all books stored in the database.")
    public List<BookDTO> getBestBooks() {
        return bookService.findBestBooks();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Find book by id", description = "Here you can find a book by it's ID.")
    public ResponseEntity<BookDTO> findBookById(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "Id of the book", example = "1")
            @PathVariable("id") Long id) {
        BookDTO bookDTO = bookService.findBookById(id);
        return bookDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookDTO);
    }

    @GetMapping("/copies/{title}")
    @Operation(summary = "Get the number of copies of a book", description = "Here you can get the number of copies of a book searched by it's title.")
    public ResponseEntity<Integer> getNumberOfCopies(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "Title of the book", example = "A Gyűrűk Ura")
            @PathVariable("title") String title) {
        Integer copies = bookService.getNumberOfCopies(title);
        return copies == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(copies);
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Find book by title", description = "Here you can find a book by it's title.")
    public ResponseEntity<BookDTO> findBookByTitle(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "Title of the book", example = "A Gyűrűk Ura")
            @PathVariable("title") String title) {
        BookDTO bookDTO = bookService.findBookByTitle(title);
        return bookDTO == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bookDTO);
    }

    @PostMapping("/manually")
    @Operation(summary = "Manually save book to database", description = "Here you can save book to database by sending a JSON with it's parameters.")
    public ResponseEntity<BookDTO> saveManually(
            //@RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid BookCommand command, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.save(command));
    }

    @PostMapping("/fromAPI")
    @Operation(summary = "Save book using Google Books API data", description = "Here you can save book to database by getting book data from Google Books API. The API command parameter must be one of: isbn, intitle, inauthor, intitle_inauthor.")
    public ResponseEntity<BookDTO> saveFromApiData(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody @Valid APICommand command, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.saveBookFomAPiDATA(command));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Here you can update book data in the database by giving its ID, and the data you want to be updated to.")
    public ResponseEntity<BookDTO> updateBook(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "Id of the book", example = "1")
            @PathVariable("id") Long id,
            @RequestBody @Valid BookCommand command, BindingResult errors) {
        if (errors.hasErrors() || bookService.findBookById(id) == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.updateBook(id, command));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book from database", description = "Here you can delete a book from the database by giving its ID.")
    public void delete(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "Id of the book", example = "1")
            @PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @PostMapping("/addauthor")
    @Operation(summary = "Add author to book", description = "Here you can add author to a book, if it is missing by default.")
    public void addAuthorToBook(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "book ID", example = "1")
            @RequestParam("bookId") String bookId,
            @Parameter(description = "name of the author", example = "Ernest Hemingway")
            @RequestParam("name") String name) {
        bookService.addAuthorToBook(bookId, name);
    }

    @PostMapping("/addgenre")
    @Operation(summary = "Add genre to book", description = "Here you can add genre to a book, if it is missing by default.")
    public void addGenreToBook(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "book ID", example = "1")
            @RequestParam("bookId") String bookId,
            @Parameter(description = "genre type", example = "LITERATURE")
            @RequestParam("genre") String genre) {
        bookService.addGenreToBook(bookId, genre);
    }

    /*@PostMapping("/addimage")
    @Operation(summary = "Add image to book", description = "Here you can add genre to a book, if it is missing by default.")
    public void addImageToBook(
            @RequestHeader(value = "Authorization") String token,
            @Parameter(description = "book ID", example = "1")
            @RequestParam("bookId") String bookId,
            @Parameter(description = "genre type", example = "LITERATURE")
            @RequestParam("genre") String genre) {
        bookService.addImageToBook(bookId, ima);
    }*/
}
