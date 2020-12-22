package com.example.demo.service;

import java.util.List;

/**
 * The interface Generic repository service.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 */
public interface GenericRepositoryService<T,U> {

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     */
    T findById(U id);

    /**
     * Save t.
     *
     * @param entity the entity
     * @return the t
     * @throws Exception the exception
     */
    T save(T entity) throws Exception;

    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     * @throws Exception the exception
     */
    T update(T entity) throws Exception;

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(U id);

    /**
     * Exist by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean existById(U id);
}
