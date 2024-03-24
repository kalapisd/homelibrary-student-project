package com.example.homelibrary.command;

import com.example.homelibrary.utils.validator.ValidAPICommandParameter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APICommand {

    @ValidAPICommandParameter
    private String parameter;

    @NotBlank(message = "Value must be valid!")
    private String value;
}
