package com.study.studywebfluxkotlinmongo.interfaces.v1.controller

import com.study.studywebfluxkotlinmongo.application.EventService
import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @GetMapping("/{id}")
    fun getEventById(@PathVariable("id") id: String): Mono<ResponseEntity<Any>> {
        return eventService.getEventById(id)
            .switchIfEmpty(Mono.just(Event("0", "0")))
            .map { event ->
                println("EventController -> EventService called ${Thread.currentThread().name}")
                if (event.id != "0") {
                    ResponseEntity(event, HttpStatus.OK)
                } else {
                    ResponseEntity("not found", HttpStatus.NOT_FOUND)
                }
            }
    }

    @GetMapping("/{id}/using-coroutine")
    suspend fun getEventByIdUsingCoroutine(@PathVariable("id") id: String): ResponseEntity<Any> {
        val result = eventService.getEventByIdUsingCoroutine(id)
        return if (result != null) {
            println("EventController -> EventService called ${Thread.currentThread().name}")
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity("not found", HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/search")
    fun searchEvent(): Mono<ResponseEntity<Any>> {
        return eventService.search()
            .map { eventResponse ->
                println("EventController -> EventService called ${Thread.currentThread().name}")
                if (eventResponse.luckyToday.totalPage != 0) {
                    ResponseEntity(eventResponse.luckyToday, HttpStatus.OK)
                } else {
                    ResponseEntity("not found", HttpStatus.NOT_FOUND)
                }
            }
    }

    @GetMapping("/search/using-coroutine")
    suspend fun searchEventUsingCoroutine(): ResponseEntity<Any> {
        val result = eventService.searchUsingCoroutine()
        return if (result.luckyToday.totalPage != 0) {
            println("EventController -> EventService called ${Thread.currentThread().name}")
            ResponseEntity(result, HttpStatus.OK)
        } else {
            ResponseEntity("not found", HttpStatus.NOT_FOUND)
        }
    }

}