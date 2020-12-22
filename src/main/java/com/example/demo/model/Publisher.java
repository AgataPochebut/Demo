package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * The type Publisher.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PUBLISHERS")
public class Publisher extends BaseEntity {

    @NotNull(message = "Book must not be null")
    @OneToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Author must not be null")
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

}
