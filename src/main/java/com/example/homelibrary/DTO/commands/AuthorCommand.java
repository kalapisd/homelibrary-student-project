package com.example.homelibrary.DTO.commands;

import com.example.homelibrary.utils.validator.ValidTitle;
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

    @NotEmpty(message = "Author name can't be empty!")
    @Size(min = 1, max = 200, message = "Author name must be between 1 and 200 characters!")
    @ValidTitle
    private String name;
}
