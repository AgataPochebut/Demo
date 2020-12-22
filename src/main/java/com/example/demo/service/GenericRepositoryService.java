package com.example.demo.service;

import java.util.List;

public interface GenericRepositoryService<T,U> {

    List<T> findAll();

    T findById(U id);

    T save(T entity) throws Exception;

    T update(T entity) throws Exception;

    void deleteById(U id);

    boolean existById(U id);
}
