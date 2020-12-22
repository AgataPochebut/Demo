package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends GenericRepository<Author, Long> {

    Optional<Author> findByName(String s);

}
