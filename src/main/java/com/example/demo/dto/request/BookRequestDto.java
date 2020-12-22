package com.example.demo.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class BookRequestDto {

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 13)
    String ISBN;

    @NotNull
    Long author;

}
