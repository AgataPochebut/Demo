package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * The type Error response dto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponseDto {

    private HttpStatus httpStatus;

    private String message;

}
