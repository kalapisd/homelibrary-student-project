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
}
