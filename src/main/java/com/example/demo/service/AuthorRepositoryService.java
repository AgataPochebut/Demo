package com.example.demo.service;

import com.example.demo.dto.request.AuthorGetRequestDto;
import com.example.demo.model.Author;

import java.util.List;

public interface AuthorRepositoryService extends GenericRepositoryService<Author, Long> {

    Author findByName(String s);

    List<Author> findAll(AuthorGetRequestDto requestDto);

}
