package com.example.demo.service;

import com.example.demo.dto.request.PublisherGetRequestDto;
import com.example.demo.model.Publisher;

import java.util.List;

/**
 * The interface Publisher repository service.
 */
public interface PublisherRepositoryService extends GenericRepositoryService<Publisher, Long> {

    /**
     * Find all list.
     *
     * @param requestDto the request dto
     * @return the list
     */
    List<Publisher> findAll(PublisherGetRequestDto requestDto);

}
