package com.jicay.bookmanagement.infrastructure.driving.controller

import com.jicay.bookmanagement.domain.model.Book
import com.jicay.bookmanagement.domain.service.BookService
import com.jicay.bookmanagement.infrastructure.driving.controller.dto.BookDTO
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @GetMapping
    fun getBooks(): List<BookDTO> {
        return bookService.getAllBooks().map { book ->
            BookDTO(
                name = book.name,
                author = book.author,
            )
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody @Valid bookDTO: BookDTO): BookDTO {
        val book = Book(
            name = bookDTO.name,
            author = bookDTO.author
        )
        bookService.addBook(book)
        return bookDTO
    }
}