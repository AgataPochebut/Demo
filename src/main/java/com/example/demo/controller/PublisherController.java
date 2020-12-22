package com.example.demo.controller;

import com.example.demo.dto.request.PublisherGetRequestDto;
import com.example.demo.dto.request.PublisherRequestDto;
import com.example.demo.dto.response.PublisherResponseDto;
import com.example.demo.model.Publisher;
import com.example.demo.service.PublisherRepositoryService;
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

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherRepositoryService repositoryService;

    @Autowired
    private Mapper mapper;

    @GetMapping
    public ResponseEntity<List<PublisherResponseDto>> get(@Valid PublisherGetRequestDto requestDto) {
        final List<Publisher> entity = repositoryService.findAll(requestDto);
        final List<PublisherResponseDto> responseDto = entity.stream()
                .map((i) -> mapper.map(i, PublisherResponseDto.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PublisherResponseDto> get(@PathVariable Long id) {
        if(!repositoryService.existById(id)) throw new EntityNotFoundException("Entity with this id not exist");
        Publisher entity = repositoryService.findById(id);
        final PublisherResponseDto responseDto = mapper.map(entity, PublisherResponseDto.class);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody PublisherRequestDto requestDto) throws Exception {
        Publisher entity = mapper.map(requestDto, Publisher.class);
        entity = repositoryService.save(entity);
        return new ResponseEntity<>(entity.getId(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void update(@PathVariable Long id, @Valid @RequestBody PublisherRequestDto requestDto) throws Exception {
        if(!repositoryService.existById(id)) throw new EntityNotFoundException("Entity with this id not exist");
        Publisher entity = mapper.map(requestDto, Publisher.class);
        entity.setId(id);
        repositoryService.update(entity);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        if(!repositoryService.existById(id)) throw new EntityNotFoundException("Entity with this id not exist");
        repositoryService.deleteById(id);
    }

}