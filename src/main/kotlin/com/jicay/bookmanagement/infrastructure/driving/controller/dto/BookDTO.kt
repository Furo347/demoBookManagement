package com.jicay.bookmanagement.infrastructure.driving.controller.dto

import jakarta.validation.constraints.NotBlank

data class BookDTO(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,

    @field:NotBlank(message = "Author must not be blank")
    val author: String
)