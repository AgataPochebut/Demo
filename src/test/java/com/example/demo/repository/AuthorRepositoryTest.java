package com.example.demo.repository;

import com.example.demo.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@Sql(scripts = "/insert_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(repository).isNotNull();
    }

    @Test
    void findAll() {
        List<Author> list = repository.findAll();
        assertThat(list).isNotNull();
        assertThat(list).isNotEmpty();
    }

    @Test
    void findByName() {
        Author obj = repository.findByName("tolstoy").orElse(null);
        assertThat(obj).isNotNull();
    }

    @Test
    void findById() {
        Author obj = repository.findById(1L).orElse(null);
        assertThat(obj).isNotNull();
    }

    @Test
    void save() throws Exception {
        Author obj = new Author();
        obj.setName("dostoyevskiy");
        repository.save(obj);

        Author obj1 = repository.findByName("dostoyevskiy").orElse(null);
        assertThat(obj1).isNotNull();
    }

    @Test
    void deleteById() {
        repository.deleteById(1L);
        Author obj = repository.findById(1L).orElse(null);
        assertThat(obj).isNull();
    }

    @Test
    void saveReturnNameNotUnique() throws Exception {
        Author obj = new Author();
        obj.setName("tolstoy");
        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> repository.save(obj));
    }

    @Test
    void saveReturnNameNotNull() throws Exception {
        Author obj = new Author();
        obj.setName(null);
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> repository.save(obj));
    }

    @Test
    void saveReturnNameNotEmpty() throws Exception {
        Author obj = new Author();
        obj.setName("");
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> repository.save(obj));
    }


}