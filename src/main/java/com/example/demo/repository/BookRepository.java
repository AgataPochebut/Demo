package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Book repository.
 */
@Repository
public interface BookRepository extends GenericRepository<Book, Long> {

    /**
     * Find by isbn optional.
     *
     * @param s the s
     * @return the optional
     */
    Optional<Book> findByISBN(String s);

}
