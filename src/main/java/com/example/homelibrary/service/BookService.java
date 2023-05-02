package com.example.homelibrary.service;

import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.DTO.commands.APICommand;
import com.example.homelibrary.DTO.commands.BookCommand;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.mapper.BookMapper;
import com.example.homelibrary.repository.BookRepository;
import com.example.homelibrary.utils.GoogleBooksApiConnection;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final GoogleBooksApiConnection apiConnection;
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper mapper;

    public BookService(GoogleBooksApiConnection apiConnection, BookRepository bookRepository, AuthorService authorService, GenreService genreService, BookMapper mapper) {
        this.apiConnection = apiConnection;
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
        this.mapper = mapper;
    }

    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    public BookDTO findBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return mapper.toDTO(book.get());
        } else {
            logger.info("No such book was found with id: {}!", id);
            return null;
        }
    }

    public BookDTO findBookByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);
        if (book.isPresent()) {
            return mapper.toDTO(book.get());
        } else {
            logger.info("No such book was found with title: {}!", title);
            return null;
        }
    }

    public List<BookDTO> findAllBooksOfAuthor(String name) {
        List<Book> books = bookRepository.findAllByAuthors_NameOrderByTitle(name);
        return books.stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public BookDTO saveBookFomAPiDATA(APICommand apiCommand) {
        BookCommand command = getBookFromAPI(apiCommand);
        return save(command);
    }

    public BookDTO save(BookCommand command) {
        Set<Author> authors = authorService.getAuthorsToSaveBook(command.getAuthors());
        Book book = bookRepository.findByTitle(command.getTitle()).orElseGet(() -> buildBook(command));
        book.addPiece();

        if (!authors.isEmpty()) {
            for (Author author : authors) {
                author.addBook(book);
            }
        }
        return mapper.toDTO(bookRepository.save(book));
    }

    private BookCommand getBookFromAPI(APICommand apiCommand) {
        BookCommand command;
        try {
            command = apiConnection.getDataFromGoogle(apiCommand);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return command;
    }

    private Book buildBook(BookCommand command) {

        Book book = Book.builder()
                .title(command.getTitle())
                .subTitle(command.getSubTitle())
                .authors(new HashSet<>())
                .publishedYear(command.getPublishedYear())
                .numOfPages(command.getNumOfPages())
                .language(command.getLanguage())
                .piece(0)
                .build();

        if (command.getGenre() != null) {
            Genre genre = genreService.getGenreByType(command.getGenre()).get();
            genre.addBook(book);
        }
        return book;
    }

    @Transactional
    public BookDTO updateBook(Long id, BookCommand command) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            logger.info("No such book was found with id: {}!", id);
            return null;
        } else {
            logger.info("Book with id: {} was updated successfully!", id);
            return mapper.toDTO(bookRepository.save(updateValues(book.get(), command)));
        }
    }

    private Book updateValues(Book book, BookCommand command) {
        Set<Author> authors = authorService.getAuthorsToSaveBook(command.getAuthors());

        book.setTitle(command.getTitle());
        book.setSubTitle(command.getSubTitle());
        book.setAuthors(authors);
        book.setPublishedYear(command.getPublishedYear());
        book.setNumOfPages(command.getNumOfPages());
        book.setLanguage(command.getLanguage());

        return book;
    }

    @Transactional
    public void deleteBook(Long id) {

        Optional<Book> book = bookRepository.findById(id);

        int deletedRows = 0;
        if (book.isPresent()) {
            Book bookToRemove = book.get();
            if (bookToRemove.getPiece() > 1) {
                bookToRemove.removePiece();
                bookRepository.save(bookToRemove);
            } else {
                deleteBookFromAuthors(new ArrayList<>(bookToRemove.getAuthors()), bookToRemove);
                deleteBookFromGenre(bookToRemove.getGenre(), bookToRemove);
                deletedRows = bookRepository.deleteBooksById(id);
            }
        }

        if (deletedRows == 0) {
            logger.info("Book not found with id: {}!", id);
        } else {
            logger.info("Book with id: {} is deleted!", id);
        }
    }

    public void addAuthorToBook(String bookId, String name) {
        Book book = bookRepository.findById(Long.valueOf(bookId)).get();
        Optional<Author> author = authorService.getAuthorByName(name);
        if (author.isPresent()) {
            author.get().addBook(book);
        } else {
            Author generatedAuthor = authorService.saveAuthor(new Author(name));
            generatedAuthor.addBook(book);
        }
        bookRepository.save(book);
    }

    public void addGenreToBook(String bookId, String genreType) {
        Book book = bookRepository.findById(Long.valueOf(bookId)).get();
        Genre genre = genreService.getGenreByType(genreType).get();

        genre.addBook(book);
        bookRepository.save(book);
    }

    private void deleteBookFromAuthors(List<Author> authors, Book book) {
        authors.forEach(author -> author.removeBook(book));
        authors.forEach(authorService::saveAuthor);
    }

    private void deleteBookFromGenre(Genre genre, Book book) {
        genre.removeBook(book);
        genreService.save(genre);
    }
}
