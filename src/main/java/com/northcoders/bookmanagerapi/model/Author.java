package com.northcoders.bookmanagerapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String authorName;

    @Column(name = "fun_fact")
    private String funFact;
    public Author(){}
}
