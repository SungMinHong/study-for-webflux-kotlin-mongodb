package com.study.studywebfluxkotlinmongo.interfaces.v1.controller

import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import com.study.studywebfluxkotlinmongo.interfaces.v1.dto.GetHelloDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RequestMapping("/v1/hello")
@RestController
class HelloController(private val eventRepository: EventRepository) {

    @GetMapping
    fun getHelloWorld(): Mono<GetHelloDTO> {
        return Mono.just(GetHelloDTO(1, "Hello world!"))
    }

    @GetMapping("/save")
    fun saveAndSend(@RequestParam("eventName") eventName: String): Mono<Event> {
        return eventRepository
            .save(Event(UUID.randomUUID().toString(), eventName))
    }

}