package com.example.demo.converter;

import com.example.demo.model.BaseEntity;
import com.example.demo.service.BookRepositoryService;
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
class BookConverter implements CustomConverter {

    private static BookRepositoryService repositoryService;
    private static Mapper mapper;

    @Autowired
    public BookConverter(BookRepositoryService repositoryService, Mapper mapper) {
        BookConverter.repositoryService = repositoryService;
        BookConverter.mapper = mapper;
    }

    /**
     * Instantiates a new Client converter.
     */
    public BookConverter() {
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
