package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import com.northcoders.bookmanagerapi.repository.BookManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookManagerServiceImpl implements BookManagerService {

    @Autowired
    BookManagerRepository bookManagerRepository;

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        bookManagerRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book insertBook(Book book) {
        return bookManagerRepository.save(book);
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        //test null bookId behaviour
        return bookManagerRepository.findById(bookId);
    }

    @Override
    public Book updateBook(Book book) {
        return bookManagerRepository.save(book);
    }
    @Override
    public boolean deleteBookById(Long bookId) {
        if (bookManagerRepository.existsById(bookId)) {
            bookManagerRepository.deleteById(bookId);
            return true;
        }
        return false;

    }

    @Override
    public List<Book> getAllBooksByGenre(Genre genre) {
        return bookManagerRepository.findByGenre(genre);
    }


}
