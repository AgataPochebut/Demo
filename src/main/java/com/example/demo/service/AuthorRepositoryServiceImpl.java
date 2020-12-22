package com.example.demo.service;

import com.example.demo.dto.request.AuthorGetRequestDto;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorRepositoryServiceImpl extends GenericRepositoryServiceImpl<Author, Long> implements AuthorRepositoryService {

    @Autowired
    private AuthorRepository repository;

    @Override
    public Author findByName(String s) {
        return repository.findByName(s).orElse(null);
    }

    @Override
    public List<Author> findAll(AuthorGetRequestDto requestDto) {
        Specification<Author> specification = Specification.where(null);

        if (requestDto.getName()!=null) {
            specification = specification.and(hasName(requestDto.getName()));
        }

        return repository.findAll(specification);
    }

    private Specification<Author> hasName(String s){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + s + "%");
            return predicate;
        };
    }

    @Override
    public Author save(Author entity) throws Exception {
        Author exist = findByName(entity.getName());
        if(exist!=null) throw new EntityExistsException("Entity with this name already exist");
        return super.save(entity);
    }

    @Override
    public Author update(Author entity) throws Exception {
        if(!existById(entity.getId())) throw new EntityNotFoundException("Entity with this id not exist");
        Author exist = findByName(entity.getName());
        if(exist!=null && exist.getId()!=entity.getId()) throw new EntityExistsException("Entity with this name already exist");
        return super.save(entity);
    }
}
