package com.jicay.bookmanagement.infrastructure.application

import com.jicay.bookmanagement.domain.model.Book
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DomainObjectsConfiguration {

    @Bean
    fun books(): MutableList<Book> {
        return mutableListOf()
    }
}