package com.example.demo.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The type Publisher request dto.
 */
@Data
@NoArgsConstructor
public class PublisherRequestDto {

    @NotNull
    private Long book;

    @NotNull
    private Long author;

}
