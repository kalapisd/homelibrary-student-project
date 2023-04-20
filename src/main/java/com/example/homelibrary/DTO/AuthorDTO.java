package com.example.homelibrary.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AuthorDTO {

    private String name;
    private List<String> books;
}
