package com.example.homelibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeLibraryApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner run(BookService service) {
        APICommand command = APICommand.builder()
                .parameter("ISBN")
                .value("963071454x")
                .build();


        return args -> {
            service.saveBookFomAPiDATA(command);
        };
    }*/
}
