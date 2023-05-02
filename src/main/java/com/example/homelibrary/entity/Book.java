package com.example.homelibrary.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subTitle;

    @ManyToMany(cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_authors",
            joinColumns = {
                    @JoinColumn(
                            name = "book_id",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "author_id",
                            referencedColumnName = "id"
                    )
            }
    )
    private Set<Author> authors = new HashSet<>();

    @ManyToOne(cascade = {
            CascadeType.MERGE
    })
    @JoinColumn(name = "genre_id")
    private Genre genre;

    private int publishedYear;

    private int numOfPages;

    private String language;

    private int piece;

    public void addPiece() {
        piece++;
    }

    public void removePiece() {
        piece--;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getTitle().equals(book.getTitle()) && getAuthors().equals(book.getAuthors());
    }
}
