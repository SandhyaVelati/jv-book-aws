package com.northcoders.bookmanagerapi.service;

import com.northcoders.bookmanagerapi.model.Author;
import com.northcoders.bookmanagerapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Long insertAuthor(Author author){
        if(author == null)
        {
            throw new IllegalArgumentException("Author of the book cannot be Empty");
        }
        Author existingAuthor = authorRepository.findByAuthorName(author.getAuthorName());
        if(existingAuthor != null){
            return existingAuthor.getId();
        }
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor.getId();

    }
}
