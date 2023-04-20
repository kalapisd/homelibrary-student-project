package com.example.homelibrary.repository;

import com.example.homelibrary.entity.Genre;
import com.example.homelibrary.entity.GenreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findById(long id);

    Optional<Genre> findByGenre(GenreType genretype);
}
