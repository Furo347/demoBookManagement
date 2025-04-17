package com.jicay.bookmanagement.domain.service

import com.jicay.bookmanagement.domain.model.Book
import org.springframework.stereotype.Service

@Service
class BookService(private val books: MutableList<Book>) {

    fun getAllBooks(): List<Book> = books

    fun addBook(book: Book) {
        books.add(book)
    }
}