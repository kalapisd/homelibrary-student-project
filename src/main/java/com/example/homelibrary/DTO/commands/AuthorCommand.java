package com.example.homelibrary.DTO.commands;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorCommand {

    @NotEmpty(message = "Author name can't be empty")
    @Size(min = 1, max = 200, message = "Enter name between 1 and 200 characters")
    private String name;

}
