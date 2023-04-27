package com.example.homelibrary.data;

import com.example.homelibrary.testmodels.APICommand;

public interface TestApiCommand {

    APICommand APIQUERY = APICommand.builder()
            .parameter("ISBN")
            .value("9638386940")
            .build();
}
