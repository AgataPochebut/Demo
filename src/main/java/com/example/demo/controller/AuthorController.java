package com.example.demo.controller;

import com.example.demo.dto.request.AuthorGetRequestDto;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.response.AuthorResponseDto;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorRepositoryService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Author controller.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepositoryService repositoryService;

    @Autowired
    private Mapper mapper;

    /**
     * Get response entity.
     *
     * @param requestDto the request dto
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> get(@Valid AuthorGetRequestDto requestDto) {
        final List<Author> list = repositoryService.findAll(requestDto);
        final List<AuthorResponseDto> responseDto = list.stream()
                .map((i) -> mapper.map(i, AuthorResponseDto.class))
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
    public ResponseEntity<AuthorResponseDto> get(@PathVariable Long id) {
        if(!repositoryService.existById(id)) throw new EntityNotFoundException("Entity with this id not exist");
        Author entity = repositoryService.findById(id);
        final AuthorResponseDto responseDto = mapper.map(entity, AuthorResponseDto.class);
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
    public ResponseEntity<Long> save(@Valid @RequestBody AuthorRequestDto requestDto) throws Exception {
        Author entity = mapper.map(requestDto, Author.class);
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
    public void update(@PathVariable Long id, @Valid @RequestBody AuthorRequestDto requestDto) throws Exception {
        Author entity = mapper.map(requestDto, Author.class);
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
