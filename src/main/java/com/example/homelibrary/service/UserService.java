package com.example.homelibrary.service;

import com.example.homelibrary.DTO.UserDTO;
import com.example.homelibrary.command.RegisterCommand;
import com.example.homelibrary.entity.User;
import com.example.homelibrary.mapper.UserMapper;
import com.example.homelibrary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    UserMapper mapper;

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public Optional<User> findUserByName(String email) {
        return userRepository.findByUserName(email);
    }

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .sorted()
                .toList();
    }

    public User saveUser(RegisterCommand command) {
        User user = mapper.fromCommand(command);
        return userRepository.save(user);
    }
}