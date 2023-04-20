package com.example.homelibrary.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String title;
    private List<String> authors;
    private String genre;
}
