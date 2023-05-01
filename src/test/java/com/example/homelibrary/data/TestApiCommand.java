package com.example.homelibrary.data;

import com.example.homelibrary.DTO.commands.APICommand;

public interface TestApiCommand {

    APICommand APIQUERY = APICommand.builder()
            .parameter("ISBN")
            .value("9638386940")
            .build();
}
