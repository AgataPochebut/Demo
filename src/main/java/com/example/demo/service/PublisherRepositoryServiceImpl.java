package com.example.demo.service;

import com.example.demo.dto.request.PublisherGetRequestDto;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublisherRepositoryServiceImpl extends GenericRepositoryServiceImpl<Publisher, Long> implements PublisherRepositoryService {

    @Autowired
    private PublisherRepository repository;

    @Override
    public List<Publisher> findAll(PublisherGetRequestDto requestDto) {
        Specification<Publisher> specification = Specification.where(null);

        if (requestDto.getBookISBN()!=null)
            specification = specification.and(hasBook(requestDto.getBookISBN()));

        if (requestDto.getAuthorName()!=null)
            specification = specification.and(hasAuthor(requestDto.getAuthorName()));

        return repository.findAll(specification);
    }

    private Specification<Publisher> hasBook(String s){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Publisher, Book> join = root.join("book");
            return criteriaBuilder.equal(join.get("ISBN"), s);
        };
    }

    private Specification<Publisher> hasAuthor(String s){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Publisher, Author> join = root.join("author");
            return criteriaBuilder.like(join.get("name"), "%" + s + "%");
        };
    }

}
