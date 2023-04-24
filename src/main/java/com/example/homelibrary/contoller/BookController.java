package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.commands.APICommand;
import com.example.homelibrary.DTO.commands.BookCommand;
import com.example.homelibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findBookById(@PathVariable("id") Long id) {
        BookDTO bookDTO = bookService.findBookById(id);

        if (bookDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bookDTO);
        }
    }

    @GetMapping("/{title}")
    public ResponseEntity<BookDTO> findBookByTitle(@PathVariable("title") String title) {
        BookDTO bookDTO = bookService.findBookByTitle(title);

        if (bookDTO == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(bookDTO);
        }
    }

    @PostMapping("/manually")
    public ResponseEntity<BookDTO> saveManually(@RequestBody @Valid BookCommand command, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.save(command));
    }

    @PostMapping("/fromAPI")
    public ResponseEntity<BookDTO> saveFromApiData(@RequestBody @Valid APICommand command, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.saveBookFomAPiDATA(command));
    }

    @PutMapping("{/id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id, @RequestBody @Valid BookCommand command, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.updateBook(id, command));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @PostMapping("/addartist")
    public void addAuthorToBook(@RequestParam("bookId") Long bookId,
                                @RequestParam("name") String name) {
        bookService.addAuthorToBook(bookId, name);
    }

    @PostMapping("/addgenre")
    public void addGenreToBook(@RequestParam("bookId") Long bookId,
                               @RequestParam("genre") String genre) {
        bookService.addGenreToBook(bookId, genre);
    }

}
