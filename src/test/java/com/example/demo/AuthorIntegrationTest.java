package com.example.demo;

import com.example.demo.dto.request.AuthorGetRequestDto;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.request.BookRequestDto;
import com.example.demo.dto.response.AuthorResponseDto;
import com.example.demo.dto.response.ErrorResponseDto;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorRepositoryService;
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
@Sql(scripts = "/insert_author.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AuthorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mapper mapper;

    @Autowired
    private AuthorRepositoryService repositoryService;

    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andReturn();

        List<AuthorResponseDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<AuthorResponseDto>>(){});
        assertThat(list.size()).isEqualTo(repositoryService.findAll().size());

        for(AuthorResponseDto dto : list) {
            assertThat(dto).isEqualTo(mapper.map(repositoryService.findById(dto.getId()), AuthorResponseDto.class));
        }
    }

    @Test
    void getByFields() throws Exception {
        MultiValueMap map = new LinkedMultiValueMap<String, String>();
        map.add("name","tol");

        MvcResult mvcResult = this.mockMvc.perform(get("/authors")
                .params(map))
                .andExpect(status().isOk())
                .andReturn();

        List<AuthorResponseDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<AuthorResponseDto>>(){});
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    void getById() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/authors/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        AuthorResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AuthorResponseDto.class);
        assertThat(dto).isEqualTo(mapper.map(repositoryService.findById(1L), AuthorResponseDto.class));
    }

    @Test
    void save() throws Exception {
        AuthorRequestDto dto = new AuthorRequestDto();
        dto.setName("test_new");

        MvcResult mvcResult = this.mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Long.class);
        Author obj = repositoryService.findById(id);
        assertThat(obj.getName()).isEqualTo(dto.getName());
    }

    @Test
    void update() throws Exception {
        AuthorRequestDto dto = new AuthorRequestDto();
        dto.setName("test_upd");

        this.mockMvc.perform(put("/authors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        Author obj = repositoryService.findById(1L);
        assertThat(obj.getName()).isEqualTo(dto.getName());
    }

    @Test
    void deleteById() throws Exception {
        this.mockMvc.perform(delete("/authors/{id}", 1L))
                .andExpect(status().isOk());

        assertThat(repositoryService.existById(1L)).isFalse();
    }

    @Test
    void getByIdReturnNotFound() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/authors/{id}", 10L))
                .andExpect(status().isNotFound())
                .andReturn();

        ErrorResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);
        assertThat(dto.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void saveReturnBadRequest() throws Exception {
        AuthorRequestDto requestDto = new AuthorRequestDto();
        requestDto.setName("tolstoy");

        MvcResult mvcResult = this.mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        ErrorResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponseDto.class);
        assertThat(dto.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

}