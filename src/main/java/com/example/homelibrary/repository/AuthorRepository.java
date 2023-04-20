package com.example.homelibrary.repository;

import com.example.homelibrary.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findAuthorByName(String name);

    List<Author> findAllByBooks_Id(Long bookId);

}
