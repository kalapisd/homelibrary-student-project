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
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private String title;
    private List<String> authors;
    private String genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDTO bookDTO)) return false;
        return Objects.equals(getTitle(), bookDTO.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
