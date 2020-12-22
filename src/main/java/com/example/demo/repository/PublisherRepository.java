package com.example.demo.repository;

import com.example.demo.model.Publisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends GenericRepository<Publisher, Long> {

}
