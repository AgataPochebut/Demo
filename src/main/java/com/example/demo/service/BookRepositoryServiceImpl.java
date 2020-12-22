package com.example.demo.service;

import com.example.demo.dto.request.BookGetRequestDto;
import com.example.demo.dto.request.BookRequestDto;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
@Transactional
public class BookRepositoryServiceImpl extends GenericRepositoryServiceImpl<Book, Long> implements BookRepositoryService {

    @Autowired
    private BookRepository repository;

    @Override
    public Book findByISBN(String s) {
        return repository.findByISBN(s).orElse(null);
    }

    @Override
    public List<Book> findAll(BookGetRequestDto requestDto) {
        Specification<Book> specification = Specification.where(null);

        if (requestDto.getISBN() != null) {
            specification = specification.and(hasISBN(requestDto.getISBN()));
        }
        if (requestDto.getAuthorName() != null) {
            specification = specification.and(hasAuthor(requestDto.getAuthorName()));
        }

        return repository.findAll(specification);
    }

    private Specification<Book> hasISBN(String s) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get("ISBN"), s);
            return predicate;
        };
    }

    private Specification<Book> hasAuthor(String s) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Book, Author> join = root.join("author");
            return criteriaBuilder.like(join.get("name"), "%" + s + "%");
        };
    }

    @Override
    public Book save(Book entity) throws Exception {
        Book exist = findByISBN(entity.getISBN());
        if(exist!=null) throw new EntityExistsException("Entity with this ISBN already exist");
        return super.save(entity);
    }

    @Override
    public Book update(Book entity) throws Exception {
        if(!existById(entity.getId())) throw new EntityNotFoundException("Entity with this id not exist");
        Book exist = findByISBN(entity.getISBN());
        if(exist!=null && exist.getId()!=entity.getId()) throw new EntityExistsException("Entity with this ISBN already exist");
        return super.save(entity);
    }
}
