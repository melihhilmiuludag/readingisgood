package com.mhu.core.book.domain.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer quantityInStock;
    private Integer totalPages;


}
