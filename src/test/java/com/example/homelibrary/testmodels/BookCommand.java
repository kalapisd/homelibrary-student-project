package com.example.homelibrary.testmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookCommand {

    private String title;
    private String subTitle;
    private List<String> authors;
    private String genre;
    private int publishedYear;
    private int numOfPages;
    private String language;

    public BookCommand(String title, String subTitle, List<String> authors, String genre, int publishedYear, int numOfPages, String language) {
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.numOfPages = numOfPages;
        this.language = language;
    }
}
