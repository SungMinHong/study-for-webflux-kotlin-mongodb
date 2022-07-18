package com.study.studywebfluxkotlinmongo.interfaces.v1.controller

import com.study.studywebfluxkotlinmongo.application.EventService
import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RequestMapping("/v1/events")
@RestController
class EventController(
    private val eventRepository: EventRepository,
    private val eventService: EventService
) {
    @GetMapping
    fun getAll(): Flux<Event> =
        eventService.getAll()
            .flatMap {
                println("EventController -> EventService called ${Thread.currentThread().name}") // 몽고디비 조회 이후라면 다른 스레드에서 실행됨
                Flux.just(it)
            }


    @PostMapping
    fun save(@RequestParam("eventName") eventName: String): Mono<Event> {
        return eventRepository
            .save(Event(UUID.randomUUID().toString(), eventName))
    }

}