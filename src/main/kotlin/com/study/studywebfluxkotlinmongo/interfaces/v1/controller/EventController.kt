package com.study.studywebfluxkotlinmongo.interfaces.v1.controller

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
    fun getAll(): Flux<Event> {
        println("EventController_1 ${Thread.currentThread().name}")
        val a =  eventService.getAll()
            .map {
                println("EventController_2 ${Thread.currentThread().name}") // 다른 스레드에서 실행됨
                Event(it.id, it.name+ " renew")
            }
        println("EventController_3 ${Thread.currentThread().name}")
        return a
    }

    @PostMapping
    fun save(@RequestParam("eventName") eventName: String): Mono<Event> {
        return eventRepository
            .save(Event(UUID.randomUUID().toString(), eventName))
    }

}