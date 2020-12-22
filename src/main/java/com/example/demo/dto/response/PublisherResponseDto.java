package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * The type Publisher response dto.
 */
@Data
@NoArgsConstructor
public class PublisherResponseDto {

    private Long id;

    private Long book;

    private Long author;

}
