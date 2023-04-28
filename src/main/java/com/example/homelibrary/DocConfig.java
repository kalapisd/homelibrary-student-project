package com.example.homelibrary;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact()
                .name("Dorottya Kalapis")
                .email("kalapisd@gmail.com");


        return new OpenAPI()
                .info(new Info()
                        .title("Home library")
                        .version("1.0.0")
                        .description("Manage your home library")
                        .contact(contact));
    }


}

