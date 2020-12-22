package com.example.demo.configuration;

import com.example.demo.converter.AuthorConverter;
import com.example.demo.converter.BookConverter;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.request.BookRequestDto;
import com.example.demo.dto.request.PublisherRequestDto;
import com.example.demo.dto.response.AuthorResponseDto;
import com.example.demo.dto.response.BookResponseDto;
import com.example.demo.dto.response.PublisherResponseDto;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.Publisher;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MappingConfiguration {

    @Bean
    public Mapper mapper() {

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {

                mapping(PublisherRequestDto.class, Publisher.class)
                        .fields("Author", "Author", FieldsMappingOptions.customConverter(AuthorConverter.class))
                        .fields("Book", "Book", FieldsMappingOptions.customConverter(BookConverter.class));

                mapping(AuthorRequestDto.class, Author.class)
                        .fields("Name", "Name");

                mapping(BookRequestDto.class, Book.class)
                        .fields("ISBN", "ISBN")
                        .fields("Author", "Author", FieldsMappingOptions.customConverter(AuthorConverter.class));


                mapping(Publisher.class, PublisherResponseDto.class)
                        .fields("Author", "Author", FieldsMappingOptions.customConverter(AuthorConverter.class))
                        .fields("Book", "Book", FieldsMappingOptions.customConverter(BookConverter.class));

                mapping(Author.class, AuthorResponseDto.class)
                        .fields("Name", "Name");

                mapping(Book.class, BookResponseDto.class)
                        .fields("ISBN", "ISBN")
                        .fields("Author", "Author", FieldsMappingOptions.customConverter(AuthorConverter.class));

            }
        });

        return mapper;
    }
}



