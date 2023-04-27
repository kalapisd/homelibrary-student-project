package com.example.homelibrary.testmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class APICommand {

    private String parameter;
    private String value;

}
