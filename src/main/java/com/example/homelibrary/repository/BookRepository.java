package com.example.homelibrary.repository;

import com.example.homelibrary.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    int deleteBooksById(Long id);

    Optional<Book> findByTitle(String title);

    List<Book> findAllByAuthors_Name(String name);
}
