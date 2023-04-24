package com.example.homelibrary.utils;

import com.example.homelibrary.DTO.commands.APICommand;
import com.example.homelibrary.DTO.commands.BookCommand;
import com.example.homelibrary.mapper.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleBooksApiConnection {

    @Value("AIzaSyAjER9gZjSb_YnJHsVNhl1fDWRK9y_o88g")
    private String apiKey;

    private BookMapper mapper;

    private RestTemplate restTemplate = new RestTemplate();

    static final Logger log = LoggerFactory.getLogger(GoogleBooksApiConnection.class);

    @Autowired
    public GoogleBooksApiConnection(BookMapper mapper) {
        this.mapper = mapper;
    }

    public BookCommand getDataFromGoogle(APICommand apiCommand) {

        String parameter = apiCommand.getParameter();
        String value = apiCommand.getValue();

        String query = switch (parameter) {
            case "ISBN" -> "isbn:" + value;
            case "inTitle" -> "intitle:" + value;
            case "inAuthor" -> "inauthor:" + value;
            case "inTitle_inAuthor" -> "intitle:" + value.split("_")[0] + "+inauthor:" + value.split("_")[1];
            default -> throw new IllegalStateException("Unexpected value: " + parameter);
        };


        BookResponse bookResponse = restTemplate.getForObject(
                "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey, BookResponse.class);

        VolumeInfo info = bookResponse.getItems().get(0).getVolumeInfo();
        BookCommand command = mapper.toCommand(info);
        log.info(command.toString());
        return command;

    }

}
