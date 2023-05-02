package com.example.homelibrary.data;

import com.example.homelibrary.DTO.commands.APICommand;

public interface TestApiCommand {

    APICommand APIQUERY = new APICommand("isbn", "9638386940");
    APICommand APIQUERY2 = new APICommand("isbn", "9789639973091");
}
