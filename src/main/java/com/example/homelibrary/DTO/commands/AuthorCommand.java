package com.example.homelibrary.DTO.commands;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthorCommand {

    @NotEmpty(message = "Author name can't be empty")
    @Size(min = 1, max = 200, message = "Enter name between 1 and 200 characters")
    private String name;

}
