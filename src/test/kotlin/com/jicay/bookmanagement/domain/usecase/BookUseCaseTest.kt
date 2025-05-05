package com.jicay.bookmanagement.domain.usecase

import com.jicay.bookmanagement.domain.model.Book
import com.jicay.bookmanagement.domain.port.BookPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify

class BookUseCaseTest : FunSpec({

    val bookPort = mockk<BookPort>()
    val bookUseCase = BookUseCase(bookPort)

    test("get all books should returns all books sorted by name") {
        every { bookPort.getAllBooks() } returns listOf(
            Book("Les Misérables", "Victor Hugo"),
            Book("Hamlet", "William Shakespeare")
        )

        val res = bookUseCase.getAllBooks()

        res.shouldContainExactly(
            Book("Hamlet", "William Shakespeare"),
            Book("Les Misérables", "Victor Hugo")
        )
    }

    test("add book") {
        justRun { bookPort.createBook(any()) }

        val book = Book("Les Misérables", "Victor Hugo")

        bookUseCase.addBook(book)

        verify(exactly = 1) { bookPort.createBook(book) }
    }

    test("reserve book") {
        val book = Book("Les Misérables", "Victor Hugo")
        every { bookPort.getAllBooks() } returns listOf(book)
        justRun { bookPort.reserveBook(book.name) }

        bookUseCase.reserveBook(book.name)

        verify(exactly = 1) { bookPort.reserveBook(book.name) }
    }

    test("reserve book should throw exception if book is not found") {
        every { bookPort.getAllBooks() } returns emptyList()

        val exception = shouldThrow<IllegalArgumentException> {
            bookUseCase.reserveBook("Unknown Book")
        }

        exception.message shouldBe "Book not found"
    }

})