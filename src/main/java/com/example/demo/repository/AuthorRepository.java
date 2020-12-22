package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Author repository.
 */
@Repository
public interface AuthorRepository extends GenericRepository<Author, Long> {

    /**
     * Find by name optional.
     *
     * @param s the s
     * @return the optional
     */
    Optional<Author> findByName(String s);

}
