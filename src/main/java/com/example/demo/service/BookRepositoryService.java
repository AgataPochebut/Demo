package com.example.demo.service;

import com.example.demo.dto.request.BookGetRequestDto;
import com.example.demo.model.Book;

import java.util.List;

/**
 * The interface Book repository service.
 */
public interface BookRepositoryService extends GenericRepositoryService<Book, Long> {

    /**
     * Find by isbn book.
     *
     * @param s the s
     * @return the book
     */
    Book findByISBN(String s);

    /**
     * Find all list.
     *
     * @param requestDto the request dto
     * @return the list
     */
    List<Book> findAll(BookGetRequestDto requestDto);

}
