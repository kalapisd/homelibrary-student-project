package com.example.homelibrary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "user_id",
            updatable = false
    )
    private long id;

    private String userName;

    private String password;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id")
    private Set<Book> books = new HashSet<>();

}
