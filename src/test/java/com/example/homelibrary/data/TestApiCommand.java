package com.example.homelibrary.data;

import com.example.homelibrary.DTO.commands.APICommand;

public interface TestApiCommand {

    APICommand APIQUERY = APICommand.builder()
            .parameter("isbn")
            .value("9638386940")
            .build();

    APICommand APIQUERY2 = APICommand.builder()
            .parameter("isbn")
            .value("9789639973091")
            .build();
}
