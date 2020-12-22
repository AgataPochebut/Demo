package com.example.demo.service;

import com.example.demo.dto.request.AuthorGetRequestDto;
import com.example.demo.model.Author;

import java.util.List;

/**
 * The interface Author repository service.
 */
public interface AuthorRepositoryService extends GenericRepositoryService<Author, Long> {

    /**
     * Find by name author.
     *
     * @param s the s
     * @return the author
     */
    Author findByName(String s);

    /**
     * Find all list.
     *
     * @param requestDto the request dto
     * @return the list
     */
    List<Author> findAll(AuthorGetRequestDto requestDto);

}
