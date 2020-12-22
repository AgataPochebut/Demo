package com.example.demo.controller;

import com.example.demo.dto.request.BookGetRequestDto;
import com.example.demo.dto.request.BookRequestDto;
import com.example.demo.dto.response.BookResponseDto;
import com.example.demo.model.Book;
import com.example.demo.service.BookRepositoryService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type Book controller.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepositoryService repositoryService;

    @Autowired
    private Mapper mapper;

    /**
     * Get response entity.
     *
     * @param requestDto the request dto
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> get(@Valid BookGetRequestDto requestDto) {
        final List<Book> entity = repositoryService.findAll(requestDto);
        final List<BookResponseDto> responseDto = entity.stream()
                .map((i) -> mapper.map(i, BookResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Get response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponseDto> get(@PathVariable Long id) {
        if(!repositoryService.existById(id)) throw new EntityNotFoundException("Entity with this id not exist");
        Book entity = repositoryService.findById(id);
        final BookResponseDto responseDto = mapper.map(entity, BookResponseDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * Save response entity.
     *
     * @param requestDto the request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody BookRequestDto requestDto) throws Exception {
        Book entity = mapper.map(requestDto, Book.class);
        entity = repositoryService.save(entity);
        return new ResponseEntity<>(entity.getId(), HttpStatus.OK);
    }

    /**
     * Update.
     *
     * @param id         the id
     * @param requestDto the request dto
     * @throws Exception the exception
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long id, @Valid @RequestBody BookRequestDto requestDto) throws Exception {
        Book entity = mapper.map(requestDto, Book.class);
        entity.setId(id);
        repositoryService.update(entity);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if(!repositoryService.existById(id)) throw new EntityNotFoundException("Entity with this id not exist");
        repositoryService.deleteById(id);
    }

}
