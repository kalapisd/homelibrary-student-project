package com.example.homelibrary.mapper;

import com.example.homelibrary.entity.Author;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.DTO.commands.BookCommand;
import com.example.homelibrary.DTO.BookDTO;
import com.example.homelibrary.utils.VolumeInfo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookCommand toCommand(VolumeInfo info) {

        if (info == null) return null;
        return BookCommand.builder()
                .title(info.getTitle())
                .subTitle(info.getSubTitle())
                .authors(info.getAuthors())
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
        bookDTO.setAuthors(book.getAuthors().stream().map(Author::getName).collect(Collectors.toList()));
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenre(String.valueOf(bookDTO.getGenre()));

        return bookDTO;
    }

}
