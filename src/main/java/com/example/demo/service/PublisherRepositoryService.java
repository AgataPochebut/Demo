package com.example.demo.service;

import com.example.demo.dto.request.PublisherGetRequestDto;
import com.example.demo.model.Publisher;

import java.util.List;

public interface PublisherRepositoryService extends GenericRepositoryService<Publisher, Long> {

    List<Publisher> findAll(PublisherGetRequestDto requestDto);

}
