package com.example.homelibrary.DTO.commands;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class APICommand {

    @NotBlank(message = "Parameter must be valid!")
    private String parameter;

    @NotBlank(message = "Value must be valid!")
    private String value;
}
