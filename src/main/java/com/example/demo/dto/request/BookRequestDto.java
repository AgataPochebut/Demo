package com.example.demo.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The type Book request dto.
 */
@Data
@NoArgsConstructor
public class BookRequestDto {

    /**
     * The Isbn.
     */
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 13)
    String ISBN;

    /**
     * The Author.
     */
    @NotNull
    Long author;

}
