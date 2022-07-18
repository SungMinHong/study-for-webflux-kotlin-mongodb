package com.study.studywebfluxkotlinmongo.application

import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import com.study.studywebfluxkotlinmongo.infra.EventClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.kotlin.core.publisher.toFlux

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventClient: EventClient
) {
    fun getAll(): Flux<Event> = eventClient.getEvent().toFlux()
        .flatMap {
            println("(1) service -> client called ${Thread.currentThread().name}")
            eventClient.getEvent().toFlux()
        }
        .flatMap {
            println("(2) service -> repository called ${Thread.currentThread().name}")
            eventRepository.findAll()
        }
        .flatMap {
            println("(3) service -> client called ${Thread.currentThread().name}")
            Flux.just(Pair(it, eventClient.getEvent()))
        }
        .flatMap {
            println("(4) service do nothing ${Thread.currentThread().name}")
            Flux.just(it.first)
        }
}