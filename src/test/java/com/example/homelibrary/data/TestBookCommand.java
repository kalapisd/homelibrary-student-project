package com.example.homelibrary.data;

import com.example.homelibrary.DTO.commands.BookCommand;

import java.util.ArrayList;
import java.util.List;

public interface TestBookCommand {

    BookCommand HARRY_POTTER = BookCommand.builder()
            .title("Harry Potter és a Bölcsek Köve")
            .authors(List.of("Joanne K. Rowling"))
            .genre("KIDS")
            .publishedYear(2000)
            .numOfPages(316)
            .language("hu")
            .build();

    BookCommand ELETED_UZLETE = BookCommand.builder()
            .title("Életed üzlete")
            .authors(List.of("Fredrik Backman"))
            .genre("LITERATURE")
            .publishedYear(2018)
            .numOfPages(78)
            .language("hu")
            .build();

    BookCommand TANCISKOLA = BookCommand.builder()
            .title("Tánciskola")
            .authors(List.of("Grecsó Krisztián"))
            .genre("LITERATURE")
            .publishedYear(2008)
            .numOfPages(330)
            .language("hu")
            .build();

    BookCommand VERA = BookCommand.builder()
            .title("Vera")
            .authors(List.of("Grecsó Krisztián"))
            .genre("LITERATURE")
            .publishedYear(2022)
            .numOfPages(327)
            .language("hu")
            .build();

    BookCommand AAT = BookCommand.builder()
            .title("Handbook on Animal-assisted Therapy")
            .subTitle("Theoretical Foundations and Guidelines for Practice")
            .authors(List.of("Aubrey H. Fine"))
            .genre("SOCIAL_SCIENCE")
            .publishedYear(2006)
            .numOfPages(534)
            .language("en")
            .build();

}
