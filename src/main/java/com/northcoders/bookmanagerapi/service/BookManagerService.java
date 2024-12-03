package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;

import java.util.List;
import java.util.Optional;

public interface BookManagerService {

    List<Book> getAllBooks();
    Book insertBook(Book book);

    Optional<Book> getBookById(Long bookId);

    //void deleteBookById(long bookId);

    Book updateBook(Book book);

    public boolean deleteBookById(Long bookId);

    List<Book> getAllBooksByGenre(Genre genre);
}
