package com.graphql.springboot.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    private Integer id;

    private String title;

    private Integer pageCount;

    private Author author;
}
