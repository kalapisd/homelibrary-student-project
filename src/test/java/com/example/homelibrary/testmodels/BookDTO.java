package com.example.homelibrary.testmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {

    private String title;
    private List<String> authors;
    private String genre;

}
