package com.example.demo;

import com.example.demo.dto.request.PublisherRequestDto;
import com.example.demo.dto.response.AuthorResponseDto;
import com.example.demo.dto.response.PublisherResponseDto;
import com.example.demo.model.Publisher;
import com.example.demo.service.PublisherRepositoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dozer.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

/**
 * The type Publisher itegration test.
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = "/insert_publisher.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PublisherItegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PublisherRepositoryService repositoryService;

    @Autowired
    private Mapper mapper;

    /**
     * Gets all.
     *
     * @throws Exception the exception
     */
    @Test
    void getAll() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/publishers"))
                .andExpect(status().isOk())
                .andReturn();

        List<PublisherResponseDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PublisherResponseDto>>(){});
        assertThat(list.size()).isEqualTo(repositoryService.findAll().size());

        for(PublisherResponseDto dto : list) {
            assertThat(dto).isEqualTo(mapper.map(repositoryService.findById(dto.getId()), PublisherResponseDto.class));
        }
    }

    /**
     * Gets by fields.
     *
     * @throws Exception the exception
     */
    @Test
    void getByFields() throws Exception {
        MultiValueMap map = new LinkedMultiValueMap<String, String>();
        map.add("book","1111111111111");
        map.add("author", "tol");

        MvcResult mvcResult = this.mockMvc.perform(get("/publishers")
                .params(map))
                .andExpect(status().isOk())
                .andReturn();

        List<PublisherResponseDto> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PublisherResponseDto>>(){});
        assertThat(list.size()).isEqualTo(1);
    }

    /**
     * Gets by id.
     *
     * @throws Exception the exception
     */
    @Test
    void getById() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/publishers/{id}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        PublisherResponseDto dto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PublisherResponseDto.class);
        assertThat(dto).isEqualTo(mapper.map(repositoryService.findById(1L), PublisherResponseDto.class));
    }

    /**
     * Save.
     *
     * @throws Exception the exception
     */
    @Test
    void save() throws Exception {
        PublisherRequestDto dto = new PublisherRequestDto();
        dto.setBook(2L);
        dto.setAuthor(2L);

        MvcResult mvcResult = this.mockMvc.perform(post("/publishers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();

        Long id = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Long.class);
        Publisher obj = repositoryService.findById(id);
        assertThat(obj.getBook().getId()).isEqualTo(dto.getBook());
        assertThat(obj.getAuthor().getId()).isEqualTo(dto.getAuthor());
    }

    /**
     * Delete by id.
     *
     * @throws Exception the exception
     */
    @Test
    void deleteById() throws Exception {
        this.mockMvc.perform(delete("/publishers/{id}", 1L))
                .andExpect(status().isOk());

        assertThat(repositoryService.existById(1L)).isFalse();
    }

}