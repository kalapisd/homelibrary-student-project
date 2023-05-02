package com.example.homelibrary.DTO.commands;

import com.example.homelibrary.utils.validator.ValidGenre;
import com.example.homelibrary.utils.validator.ValidPublishDate;
import com.example.homelibrary.utils.validator.ValidSubTitle;
import com.example.homelibrary.utils.validator.ValidTitle;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCommand {

    @NotEmpty(message = "Book title can't be empty+")
    @Size(min = 1, max = 200, message = "Book title must be between 1 and 200 characters!")
    @ValidTitle
    private String title;

    @ValidSubTitle
    private String subTitle;

    @NotEmpty(message = "Authors can't be empty!")
    private List<String> authors;

    @ValidGenre
    private String genre;

    @ValidPublishDate
    private Integer publishedYear;

    @NotNull(message = "The number of pages can't be null!")
    private Integer numOfPages;

    @NotNull(message = "Book language can't be null!")
    private String language;
}
