package com.example.demo.service;

import com.example.demo.dto.request.BookGetRequestDto;
import com.example.demo.model.Book;

import java.util.List;

public interface BookRepositoryService extends GenericRepositoryService<Book, Long> {

    Book findByISBN(String s);

    List<Book> findAll(BookGetRequestDto requestDto);

}
