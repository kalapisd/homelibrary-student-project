package com.example.homelibrary.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements Comparable<BookDTO> {

    private String title;
    private List<String> authors;
    private String genre;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookDTO bookDTO)) return false;
        return getTitle().equals(bookDTO.getTitle()) && getAuthors().equals(bookDTO.getAuthors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }

    @Override
    public int compareTo(BookDTO o) {
        Collator collator = Collator.getInstance(new Locale("hu", "HU"));
        return collator.compare(this.getTitle(), o.getTitle());
    }
}
