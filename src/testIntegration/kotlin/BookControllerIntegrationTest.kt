package com.jicay.bookmanagement.infrastructure.driving.controller

import com.jicay.bookmanagement.domain.model.Book
import com.jicay.bookmanagement.domain.service.BookService
import com.jicay.bookmanagement.infrastructure.driving.controller.dto.BookDTO
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BookController::class)
class BookControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private val bookService: BookService = mockk()

    @Test
    fun `GET books should return a list of books`() {
        every { bookService.getAllBooks() } returns listOf(
            Book(name = "Book 1", author = "Author 1"),
            Book(name = "Book 2", author = "Author 2")
        )

        mockMvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Book 1"))
            .andExpect(jsonPath("$[0].author").value("Author 1"))
    }

    @Test
    fun `POST books with invalid input should return 400`() {
        val invalidPayload = """
            {
                "name": "",
                "author": "New Author"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidPayload)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `POST books should handle domain exception and return 500`() {
        every { bookService.addBook(any()) } throws RuntimeException("Domain exception")

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "New Book",
                    "author": "New Author"
                }
            """.trimIndent())
        )
            .andExpect(status().isInternalServerError)
            .andExpect(content().string("Domain exception"))
    }
}