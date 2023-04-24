package com.example.homelibrary.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private String name;
    private List<String> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDTO authorDTO)) return false;
        return Objects.equals(getName(), authorDTO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
