package com.example.homelibrary.DTO.commands;

import com.example.homelibrary.utils.validator.ValidAPICommandParameter;
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

    @ValidAPICommandParameter
    private String parameter;

    @NotBlank(message = "Value must be valid!")
    private String value;
}
