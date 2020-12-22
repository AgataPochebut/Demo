package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BOOKS")
public class Book extends BaseEntity {

    @NotNull
    @NotEmpty
    @Size(min = 10, max = 13)
    @Column(nullable = false, unique = true)
    private String ISBN;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToOne(mappedBy = "book")
    private Publisher publisher;

}
