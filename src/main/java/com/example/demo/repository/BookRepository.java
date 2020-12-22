package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends GenericRepository<Book, Long> {

    Optional<Book> findByISBN(String s);

}
