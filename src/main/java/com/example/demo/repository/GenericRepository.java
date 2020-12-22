package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

/**
 * The interface Generic repository.
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 */
@Component
@NoRepositoryBean
public interface GenericRepository<T,U> extends JpaRepository<T,U>, JpaSpecificationExecutor<T> {
}
