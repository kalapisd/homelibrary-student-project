package com.example.homelibrary;

import com.example.homelibrary.DTO.commands.APICommand;
import com.example.homelibrary.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomeLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeLibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(BookService service) {
        APICommand command = APICommand.builder()
                .parameter("ISBN")
                .value("963071454x")
                .build();


        return args -> {
            service.saveBookFomAPiDATA(command);
        };
    }
}
