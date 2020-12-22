package com.example.demo.converter;

import com.example.demo.model.Author;
import com.example.demo.model.BaseEntity;
import com.example.demo.service.AuthorRepositoryService;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public
class AuthorConverter implements CustomConverter {

    private static AuthorRepositoryService repositoryService;
    private static Mapper mapper;

    @Autowired
    public AuthorConverter(AuthorRepositoryService repositoryService, Mapper mapper) {
        AuthorConverter.repositoryService = repositoryService;
        AuthorConverter.mapper = mapper;
    }

    /**
     * Instantiates a new Client converter.
     */
    public AuthorConverter() {
    }

    @Override
    public Object convert(Object dest, Object source, Class<?> destinationClass, Class<?> sourceClass) {
        if (source == null)
            return null;

        if (destinationClass == Long.class && source instanceof BaseEntity) {
            return ((BaseEntity) source).getId();
        }

        if (source instanceof Long) {
            return repositoryService.findById((Long) source);
        }

        return null;
    }
}
