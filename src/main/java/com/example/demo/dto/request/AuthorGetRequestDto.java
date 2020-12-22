package com.example.demo.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * The type Author get request dto.
 */
@Data
@NoArgsConstructor
public class AuthorGetRequestDto {

    private String name;

}
