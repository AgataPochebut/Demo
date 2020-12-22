package com.example.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class PublisherResponseDto {

    private Long id;

    private Long book;

    private Long author;

}
