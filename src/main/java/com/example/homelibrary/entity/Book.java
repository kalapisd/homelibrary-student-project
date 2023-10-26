package com.example.homelibrary.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    private int copies;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "rating_id")
    private Set<Rating> ratings = new HashSet<>();

    private double currentRating;

    @Lob
    @JdbcTypeCode(SqlTypes.LONG32VARBINARY)
    private byte[] image;

    public void addPiece() {
        copies++;
    }

    public void removePiece() {
        copies--;
    }

    public void setCurrentRating (Rating rating) {

        if(rating == null) {
            this.currentRating = 0;
        } else {
            this.currentRating = this.getCurrentRating() == 0 ? rating.getRatingScore() : getAverageRating(rating);
        }
    }

    private double getAverageRating(Rating rating) {
        double currentRating = this.getRatings().stream().mapToInt(Rating::getRatingScore).average().orElse(0);

        int numOfRatings = this.getRatings().size();

        return (currentRating + rating.getRatingScore()) / (numOfRatings + 1);
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
