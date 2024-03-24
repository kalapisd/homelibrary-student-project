package com.example.homelibrary.service;

import com.example.homelibrary.entity.User;
import com.example.homelibrary.repository.UserRepository;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String name) throws AuthenticationException {
        Optional<User> customer = userRepository.findByUserName(name);
        if (customer.isEmpty()) {
            throw new AuthenticationException("Invalid username") {
            };
        } else {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(customer.get().getUserName())
                    .password(customer.get().getPassword())
                    .roles("USER")
                    .build();
        }
    }
}
