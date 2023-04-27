package com.example.homelibrary.data;

import com.example.homelibrary.DTO.commands.AuthorCommand;

public interface TestAuthorCommand {

    AuthorCommand BACKMAN = new AuthorCommand("Fredrik Backman");
    AuthorCommand GRECSO = new AuthorCommand("Grecsó Krisztián");
    AuthorCommand FINE = new AuthorCommand("Aubrey H. Fine");
    AuthorCommand ROWLING = new AuthorCommand("Joanne K. Rowling");
}
