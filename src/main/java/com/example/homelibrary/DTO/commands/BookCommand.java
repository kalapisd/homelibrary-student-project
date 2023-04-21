package com.example.homelibrary.DTO.commands;

import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.utils.validator.ValidPublishDate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BookCommand {

    @NotEmpty(message = "Book title can't be empty")
    @Size(min = 1, max = 200, message = "enter book title between 1 and 200 characters")
    private String title;
    private String subTitle;
    private List<String> authors;
    private String genre;
    @ValidPublishDate
    private int publishedYear;

    @NotNull(message = "The number of pages can't be null")
    private int numOfPages;

    @NotNull(message = "Book language can't be null")
    private String language;

}
