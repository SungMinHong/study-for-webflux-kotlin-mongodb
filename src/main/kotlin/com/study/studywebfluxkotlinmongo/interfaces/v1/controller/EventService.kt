package com.study.studywebfluxkotlinmongo.interfaces.v1.controller

import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class EventService(private val eventRepository: EventRepository) {

    fun getAll(): Flux<Event> {
        println("MyService ${Thread.currentThread().name}")
        return eventRepository.findAll()
    }
}