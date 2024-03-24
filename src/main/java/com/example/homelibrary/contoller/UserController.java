package com.example.homelibrary.contoller;

import com.example.homelibrary.DTO.UserDTO;
import com.example.homelibrary.command.RegisterCommand;
import com.example.homelibrary.entity.User;
import com.example.homelibrary.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("http://localhost:3000")
@Tag(name = "Here you can manage user account details.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterCommand registerCommand, BindingResult errors) {
        if (errors.hasErrors()) return ResponseEntity.badRequest().build();
        User user = userService.saveUser(registerCommand);
        return ResponseEntity.ok(user.getUserName());
    }
}
