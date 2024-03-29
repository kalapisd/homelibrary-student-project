package com.example.homelibrary.mapper;

import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.command.BookCommand;
import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.utils.utilsdata.VolumeInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookCommand toCommand(VolumeInfo info) {

        if (info == null) return null;
        return BookCommand.builder()
                .title(info.getTitle())
                .subTitle(info.getSubTitle())
                .authors(info.getAuthors())
                .genre(null)
                .publishedYear(info.getPublishedYear())
                .numOfPages(info.getNumOfPages())
                .language(convertLanguage(info.getLanguage()))
                .build();
    }

    private String convertLanguage(String isoLanguage) {
        return switch (isoLanguage) {
            case "hu" -> "hungarian";
            case "eng" -> "english";
            default -> null;
        };
    }

    public BookDTO toDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setAuthors(book.getAuthors().stream().map(Author::getName).toList());
        bookDTO.setTitle(book.getTitle());
        if (book.getGenre() != null) {
            bookDTO.setGenre(String.valueOf(book.getGenre().getGenreType()));
        }
        bookDTO.setCurrentRating(book.getCurrentRating());
        bookDTO.setImage(Base64.encodeBase64String(book.getImage()));
        return bookDTO;
    }
}
