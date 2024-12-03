package com.northcoders.bookmanagerapi.controller;

import com.northcoders.bookmanagerapi.model.Author;
import com.northcoders.bookmanagerapi.model.Book;
import com.northcoders.bookmanagerapi.model.Genre;
import com.northcoders.bookmanagerapi.service.AuthorService;
import com.northcoders.bookmanagerapi.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookManagerController {

    @Autowired
    BookManagerService bookManagerService;

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<?> getAllBooks(@RequestParam(defaultValue = "") String genre) {
        if(genre.isBlank()){
            List<Book> books = bookManagerService.getAllBooks();
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
        for(Genre genreEnumVal : Genre.values()){
            if(genreEnumVal.toString().equalsIgnoreCase(genre))
                return new ResponseEntity<>(bookManagerService.getAllBooksByGenre(Genre.valueOf(genre)),HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid Genre",HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Long authorId = authorService.insertAuthor(book.getAuthor());

        Book newBook = bookManagerService.insertBook(book);
        //Author author = book.getAuthor();
       // System.out.println(newBook.()); // no association between book and author yet
       // newBook.setAuthor_id(authorId);   //author id is linked to book
     //   Book book1 = bookManagerService.updateBook(newBook);  //book is now updated with author id
       // System.out.println(book1.getAuthor_id());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("book", "/api/v1/book/" + newBook.getId().toString());
        return new ResponseEntity<>(newBook, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> findBookById(@PathVariable("bookId") Long bookId)
    {
        // bookManagerService
        if (bookManagerService.getBookById(bookId).isEmpty()) return new ResponseEntity<>("book with id: " +
                bookId+ "  not found",HttpStatus.BAD_REQUEST);
        Optional<Book> bookById = bookManagerService.getBookById(bookId);
        return new ResponseEntity<>(bookById.orElse(null),HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBookById(@PathVariable Long bookId,@RequestBody Book book)
    {
        if (bookManagerService.getBookById(bookId).isEmpty()) return new ResponseEntity<>("book with given Id: "+bookId +" not found",HttpStatus.BAD_REQUEST);
        if (Objects.equals(book.getId(), bookId))  return new ResponseEntity<>(bookManagerService.updateBook(book),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long bookId)
    {
        if (bookManagerService.deleteBookById(bookId)) return new ResponseEntity<>(bookId+" was deleted.",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);


    }
}
