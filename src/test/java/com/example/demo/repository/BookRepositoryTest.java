package com.example.demo.repository;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * The type Book repository test.
 */
@DataJpaTest
@Sql(scripts = "/insert_book.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    /**
     * Find all.
     */
    @Test
    void findAll() {
        List<Book> list = repository.findAll();
        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

    /**
     * Find by isbn.
     */
    @Test
    void findByISBN() {
        Book obj = repository.findByISBN("1111111111111").orElse(null);
        assertThat(obj).isNotNull();
    }

    /**
     * Find by id.
     */
    @Test
    void findById() {
        Book obj = repository.findById(1L).orElse(null);
        assertThat(obj).isNotNull();
    }

    /**
     * Save.
     */
    @Test
    void save() {
        Book obj = repository.findById(1L).orElse(null);
        repository.save(obj);

        Book obj1 = repository.findById(1L).orElse(null);
        assertThat(obj1).isNotNull();
    }

    /**
     * Delete by id.
     */
    @Test
    void deleteById() {
        repository.deleteById(1L);
        Book obj = repository.findById(1L).orElse(null);
        assertThat(obj).isNull();
    }
}