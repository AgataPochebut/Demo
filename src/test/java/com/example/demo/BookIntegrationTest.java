package com.example.demo;

import com.example.demo.dto.request.BookRequestDto;
import com.example.demo.dto.response.AuthorResponseDto;
import com.example.demo.dto.response.BookResponseDto;
import com.example.demo.dto.response.ErrorResponseDto;
import com.example.demo.model.Book;
import com.example.demo.service.BookRepositoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = "/insert_book.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class BookIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    @Autowired
    private BookRepositoryService repositoryService;

    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andReturn();

        List<BookResponseDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<BookResponseDto>>(){});
        assertThat(list.size()).isEqualTo(repositoryService.findAll().size());

        for(BookResponseDto dto : list) {
            assertThat(dto).isEqualTo(mapper.map(repositoryService.findById(dto.getId()), BookResponseDto.class));
        }
    }

    @Test
    void getByFields() throws Exception {
        MultiValueMap map = new LinkedMultiValueMap<String, String>();
        map.add("ISBN","1111111111111");
        map.add("author", "tol");

        MvcResult mvcResult = this.mockMvc.perform(get("/books")
                .params(map))
                .andExpect(status().isOk())
                .andReturn();

        List<BookResponseDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<BookResponseDto>>(){});
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void getById() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        BookResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), BookResponseDto.class);
        assertThat(dto).isEqualTo(mapper.map(repositoryService.findById(1L), BookResponseDto.class));
    }

    @Test
    void save() throws Exception {
        BookRequestDto dto = new BookRequestDto();
        dto.setISBN("3333333333333");
        dto.setAuthor(1L);

        MvcResult mvcResult = this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Long.class);
        Book obj = repositoryService.findById(id);
        assertThat(obj.getISBN()).isEqualTo(dto.getISBN());
        assertThat(obj.getAuthor().getId()).isEqualTo(dto.getAuthor());
    }

    @Test
    void update() throws Exception {
        BookRequestDto dto = new BookRequestDto();
        dto.setISBN("4444444444444");
        dto.setAuthor(1L);

        this.mockMvc.perform(put("/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        Book obj = repositoryService.findById(1L);
        assertThat(obj.getISBN()).isEqualTo(dto.getISBN());
        assertThat(obj.getAuthor().getId()).isEqualTo(dto.getAuthor());
    }

    @Test
    void deleteById() throws Exception {
        this.mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isOk());

        assertThat(repositoryService.existById(1L)).isFalse();
    }

    @Test
    void getByIdReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/books/{id}", 10L))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);
        assertThat(dto.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void saveReturnBadRequest() throws Exception {
        BookRequestDto requestDto = new BookRequestDto();
        requestDto.setISBN("1111111111111");
        requestDto.setAuthor(1L);

        MvcResult mvcResult = this.mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);
        assertThat(dto.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}