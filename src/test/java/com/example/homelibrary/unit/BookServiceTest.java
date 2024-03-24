package com.example.homelibrary.unit;

import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.command.APICommand;
import com.example.homelibrary.command.BookCommand;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.entity.GenreType;
import com.example.homelibrary.mapper.BookMapper;
import com.example.homelibrary.repository.BookRepository;
import com.example.homelibrary.service.AuthorService;
import com.example.homelibrary.service.BookService;
import com.example.homelibrary.service.GenreService;
import com.example.homelibrary.utils.GoogleBooksApiConnection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService service;

    @Mock
    GoogleBooksApiConnection apiConnection;

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorService authorService;

    @Mock
    GenreService genreService;

    @Mock
    BookMapper mapper;

    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    private static final BookDTO BOOKDTO_ONE = new BookDTO(1, "Harry Potter és a Bölcsek Köve", List.of("Joanne K. Rowling"), "KIDS", 5, null);
    private static final BookDTO BOOKDTO_TWO = new BookDTO(2, "Harry Potter és a Titkok Kamrája", List.of("Joanne K. Rowling"), "KIDS", 4, null);
    private static final Book BOOK_ONE = Book.builder().id(1L)
                .title("Harry Potter és a Bölcsek Köve")
                .subTitle(null)
                .authors(new HashSet<>())
            .genre(null)
                .publishedYear(2000)
                .numOfPages(316)
                .copies(1)
                .build();

    private static final Book BOOK_TWO = Book.builder()
            .id(2L)
                .title("Harry Potter és a Titkok Kamrája")
                .subTitle(null)
                .authors(new HashSet<>())
            .genre(null)
                .publishedYear(2001)
                .numOfPages(285)
                .copies(1)
                .build();

    @Test
    void should_return_empty_list_when_no_books_present() {

        when(bookRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(service.findAllBooks()).isEmpty();
        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_find_and_return_all_books() {

        setUpDatabaseForFindAll();

        List<BookDTO> expectedBookDTOs = List.of(
                BOOKDTO_ONE,
                BOOKDTO_TWO
        );

        List<BookDTO> actualBookDTOs = service.findAllBooks();

        assertTrue(expectedBookDTOs.size() == actualBookDTOs.size() && expectedBookDTOs.containsAll(actualBookDTOs) && actualBookDTOs.containsAll(expectedBookDTOs));

        verify(bookRepository, times(1)).findAll();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_find_and_return_all_books_of_Author() {

        setUpDatabaseForBooksOfAuthor();

        String name = "Joanne K. Rowling";

        List<BookDTO> expectedBookDTOs = List.of(
                BOOKDTO_ONE,
                BOOKDTO_TWO
        );

        List<BookDTO> actualBookDTOs = service.findAllBooksOfAuthor(name);

        assertTrue(expectedBookDTOs.size() == actualBookDTOs.size() && expectedBookDTOs.containsAll(actualBookDTOs) && actualBookDTOs.containsAll(expectedBookDTOs));

        verify(bookRepository, times(1)).findAllByAuthors_NameOrderByTitle(any());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_find_and_return_book_by_id() {

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BOOK_ONE));
        when(mapper.toDTO(any())).thenReturn(BOOKDTO_ONE);
        BookDTO actualDTO = service.findBookById(1L);
        assertEquals(BOOKDTO_ONE, actualDTO);

        verify(bookRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_not_find_book_by_id_that_doesnt_exists() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(service.findBookById(1L));

        verify(bookRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_find_and_return_bookDTO_by_title() {

        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(BOOK_ONE));
        when(mapper.toDTO(any())).thenReturn(BOOKDTO_ONE);
        BookDTO actualDTO = service.findBookByTitle("Harry Potter és a Bölcsek Köve");
        assertEquals(BOOKDTO_ONE, actualDTO);

        verify(bookRepository, times(1)).findByTitle(anyString());
        verifyNoMoreInteractions(bookRepository);
    }


    @Test
    void should_not_find_book_by_title_that_doesnt_exists() {
        String nonExistingTitle = "Biblia";

        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        assertNull(service.findBookByTitle(nonExistingTitle));

        verify(bookRepository, times(1)).findByTitle(anyString());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_return_copy_number_of_existing_book() {
        Book book = buildBookWithTwoCopies();
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(book));
        Integer numOfCopies = service.getNumberOfCopies(book.getTitle());

        assertEquals(2, numOfCopies);
        verify(bookRepository, times(1)).findByTitle(anyString());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_save_and_return_book() {

        BookCommand testCommand = BookCommand.builder()
                .title("Harry Potter és a Bölcsek Köve")
                .authors(List.of("Joanne K. Rowling"))
                .publishedYear(2000)
                .numOfPages(316)
                .build();

        APICommand apiCommand = new APICommand("isbn", "123456789");

        when(apiConnection.getDataFromGoogle(apiCommand)).thenReturn(testCommand);
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(bookRepository.save(bookArgumentCaptor.capture())).thenAnswer(a -> a.getArgument(0));

        service.saveBookFomAPiDATA(apiCommand);
        verify(bookRepository, times(1)).save(any());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_update_book_when_id_is_correct() {
        Long id = 1L;
        BookCommand bookCommand = BookCommand.builder()
                .title("Harry Potter és az Azkabani fogoly")
                .authors(null)
                .publishedYear(2022)
                .numOfPages(398)
                .build();

        when(bookRepository.findById(id)).thenReturn(Optional.of(BOOK_ONE));

        String expectedName = "Harry Potter és az Azkabani fogoly";
        when(mapper.toDTO(any())).thenReturn(BOOKDTO_ONE);
        BookDTO returnDTO = service.updateBook(1L, bookCommand);
        String actualName = returnDTO.getTitle();

        verify(bookRepository, times(1)).save(BOOK_ONE);
        assertEquals(expectedName, actualName);
    }

    @Test
    void only_one_copy_presents_delete_book_with_correctId_should_remove_it_from_genre() {

        Book book = buildOneBook();

        Genre genre = book.getGenre();
        int numOfBooksInGenre = genre.getBooksOfGenre().size();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.deleteBooksById(anyLong())).thenReturn(1);

        service.deleteBook(1L);

        int numOfBooksInGenreAfterDelete = genre.getBooksOfGenre().size();
        assertEquals(numOfBooksInGenre - 1, numOfBooksInGenreAfterDelete);
        verify(bookRepository, times(1)).deleteBooksById(anyLong());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void when_only_one_copy_presents_delete_book_with_correctId_should_remove_it_from_authors() {

        Book book = buildOneBook();

        Author author = book.getAuthors().iterator().next();
        int numOfBooksOfAuthor = author.getBooks().size();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.deleteBooksById(anyLong())).thenReturn(1);

        service.deleteBook(1L);

        int numOfBooksOfAuthorAfterDelete = author.getBooks().size();
        assertEquals(numOfBooksOfAuthor - 1, numOfBooksOfAuthorAfterDelete);
        verify(bookRepository, times(1)).deleteBooksById(anyLong());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void when_only_multiple_copes_present_delete_book_with_correctId_should_decrease_copynumber() {

        Book book = buildBookWithTwoCopies();

        Author author = book.getAuthors().iterator().next();
        int numOfBooksOfAuthor = author.getBooks().size();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(bookArgumentCaptor.capture())).thenAnswer(a -> a.getArgument(0));

        service.deleteBook(1L);

        int numOfBooksOfAuthorAfterDelete = author.getBooks().size();
        assertEquals(numOfBooksOfAuthor, numOfBooksOfAuthorAfterDelete);

        int pieceNUmber = author.getBooks().stream().map(Book::getCopies).toList().get(0);
        assertEquals(1, pieceNUmber);

        verify(bookRepository, times(1)).save(any());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_add_author_to_book() {
        Author author = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>())
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(BOOK_ONE));
        when(authorService.getAuthorByName("Joanne K. Rowling")).thenReturn(Optional.of(author));

        service.addAuthorToBook("1", "Joanne K. Rowling");
        verify(bookRepository, times(1)).save(any());
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void should_add_genre_to_book() {

        Genre kids = Genre.builder()
                .id(1L)
                .genreType(GenreType.KIDS)
                .booksOfGenre(new HashSet<>())
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(BOOK_ONE));
        when(genreService.getGenreByType("KIDS")).thenReturn(Optional.of(kids));

        service.addGenreToBook("1", "KIDS");
        verify(bookRepository, times(1)).save(any());
        verifyNoMoreInteractions(bookRepository);
    }

    private void setUpDatabaseForFindAll() {
        when(bookRepository.findAll()).thenReturn(List.of(BOOK_ONE, BOOK_TWO));
        when(mapper.toDTO(BOOK_ONE)).thenReturn(BOOKDTO_ONE);
        when(mapper.toDTO(BOOK_TWO)).thenReturn(BOOKDTO_TWO);
    }

    private void setUpDatabaseForBooksOfAuthor() {
        when(bookRepository.findAllByAuthors_NameOrderByTitle(any())).thenReturn(List.of(BOOK_ONE, BOOK_TWO));
        when(mapper.toDTO(BOOK_ONE)).thenReturn(BOOKDTO_ONE);
        when(mapper.toDTO(BOOK_TWO)).thenReturn(BOOKDTO_TWO);
    }

    private Book buildOneBook() {
        Book book1 = Book.builder()
                .id(1L)
                .title("Harry Potter és a Bölcsek Köve")
                .subTitle(null)
                .authors(new HashSet<>())
                .genre(null)
                .publishedYear(2000)
                .numOfPages(316)
                .copies(1)
                .build();

        Author author = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>(List.of(book1)))
                .build();

        book1.setAuthors(new HashSet<>(List.of(author)));

        Genre kids = Genre.builder()
                .id(1L)
                .genreType(GenreType.KIDS)
                .booksOfGenre(new HashSet<>(List.of(book1)))
                .build();

        book1.setGenre(kids);
        kids.getBooksOfGenre().forEach(book -> book.setGenre(kids));

        return book1;
    }

    private Book buildBookWithTwoCopies() {
        Book book1 = Book.builder()
                .id(1L)
                .title("Harry Potter és a Bölcsek Köve")
                .subTitle(null)
                .authors(new HashSet<>())
                .genre(null)
                .publishedYear(2000)
                .numOfPages(316)
                .copies(2)
                .build();

        Author author = Author.builder()
                .id(1L)
                .name("Joanne K. Rowling")
                .books(new HashSet<>(List.of(book1)))
                .build();

        book1.setAuthors(new HashSet<>(List.of(author)));

        Genre kids = Genre.builder()
                .id(1L)
                .genreType(GenreType.KIDS)
                .booksOfGenre(new HashSet<>(List.of(book1)))
                .build();

        book1.setGenre(kids);
        kids.getBooksOfGenre().forEach(book -> book.setGenre(kids));

        return book1;
    }
}
