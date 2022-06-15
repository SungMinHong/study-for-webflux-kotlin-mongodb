package com.study.studywebfluxkotlinmongo.interfaces.v1.controller

import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RequestMapping("/v1/events")
@RestController
class EventController(private val eventRepository: EventRepository) {

    @GetMapping
    fun getAll(): Flux<Event> {
        return eventRepository.findAll()
    }

    @PostMapping
    fun save(@RequestParam("eventName") eventName: String): Mono<Event> {
        return eventRepository
            .save(Event(UUID.randomUUID().toString(), eventName))
    }

}