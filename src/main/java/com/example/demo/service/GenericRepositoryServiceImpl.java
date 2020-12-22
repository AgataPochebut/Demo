package com.example.demo.service;

import com.example.demo.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Generic repository service.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 */
@Service
@Transactional
public abstract class GenericRepositoryServiceImpl<T, U> implements GenericRepositoryService<T, U> {

    @Autowired
    private GenericRepository<T, U> repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(U id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public T save(T entity) throws Exception {
        return repository.save(entity);
    }

    @Override
    public T update(T entity) throws Exception {
        return repository.save(entity);
    }

    @Override
    public void deleteById(U id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existById(U id) {
        return repository.existsById(id);
    }
}
