package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Book response dto.
 */
@Data
@NoArgsConstructor
public class BookResponseDto {

    private Long id;

    private String ISBN;

    private Long author;

}
