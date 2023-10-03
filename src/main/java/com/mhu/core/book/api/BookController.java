package com.mhu.core.book.api;


import com.mhu.core.book.domain.dto.BookDto;
import com.mhu.core.book.domain.dto.CreateBookRequestDto;
import com.mhu.core.book.domain.dto.UpdateBookStockRequestDto;
import com.mhu.core.book.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/book")
    @Operation(tags = "Book Service",security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<BookDto> createBook(@Validated @RequestBody CreateBookRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.addBook(requestDto));
    }

    @PatchMapping(value = "/book/{id}")//only updating stock so patch. when all data update then put. :) <3.
    @Operation(tags = "Book Service",security = @SecurityRequirement(name = "Bearer Auth"))
    public ResponseEntity<BookDto> updateBookStock(@Validated @RequestBody UpdateBookStockRequestDto requestDto,
                                                   @PathVariable(value = "id") Long id){
        return ResponseEntity.ok(bookService.updateBookStockById(requestDto,id));

    }


}
