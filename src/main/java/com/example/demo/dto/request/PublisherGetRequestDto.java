package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * The type Publisher get request dto.
 */
@Data
@NoArgsConstructor
public class PublisherGetRequestDto {

   @Size(min = 10, max = 13)
   private String bookISBN;

   private String authorName;

}
