package com.example.homelibrary.command;

import com.example.homelibrary.utils.validator.ValidPassword;
import com.example.homelibrary.utils.validator.ValidUserName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand {

    @NotEmpty(message = "Username name can't be empty!")
    @Size(min = 5, max = 10, message = "Username must be between 1 and 10 characters!")
    @ValidUserName
    private String userName;

    @ValidPassword
    private String password;
}
