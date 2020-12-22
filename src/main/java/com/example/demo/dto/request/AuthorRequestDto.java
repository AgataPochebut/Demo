package com.example.demo.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The type Author request dto.
 */
@Data
@NoArgsConstructor
public class AuthorRequestDto {

    @NotNull
    @NotEmpty
    private String name;

}
