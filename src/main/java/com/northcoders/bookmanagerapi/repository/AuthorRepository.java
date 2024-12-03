package com.northcoders.bookmanagerapi.repository;

import com.northcoders.bookmanagerapi.model.Author;
import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository  extends CrudRepository<Author, Long> {

    Author findByAuthorName(String name);

}
