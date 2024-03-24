package com.example.homelibrary.mapper;

import com.example.homelibrary.DTO.UserDTO;
import com.example.homelibrary.command.RegisterCommand;
import com.example.homelibrary.entity.Book;
import com.example.homelibrary.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(userDTO.getName());
        userDTO.setBooks(user.getBooks().stream().map(Book::getTitle).toList());

        return userDTO;
    }

    public User fromCommand(RegisterCommand command) {
        User user = new User();
        user.setUserName(command.getUserName());
        user.setPassword(command.getPassword());
        return user;
    }
}
