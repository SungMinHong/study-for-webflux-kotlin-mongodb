package com.study.studywebfluxkotlinmongo.application

import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import com.study.studywebfluxkotlinmongo.infra.EventClient
import com.study.studywebfluxkotlinmongo.infra.EventSearchResponse
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Service
class EventService(
    private val eventRepository: EventRepository,
    private val eventClient: EventClient
) {
    fun getAll(): Flux<Event> {
        println("(0) service -> client called ${Thread.currentThread().name}")
        return eventClient.getEvent().toFlux()
            .flatMap {
                println("(1)[flatMap] service -> client called ${Thread.currentThread().name}")
                eventClient.getEvent().toFlux()
            }
            .flatMap {
                println("(2)[flatMap] service -> repository called ${Thread.currentThread().name}")
                eventRepository.findAll()
            }
    }

    fun getEventById(id: String): Mono<Event> {
        println("service -> repository called ${Thread.currentThread().name}")
        return eventRepository.findById(id)
    }

    suspend fun getEventByIdUsingCoroutine(id: String): Event? {
        println("service -> repository called ${Thread.currentThread().name}")
        return eventRepository.findById(id).awaitSingleOrNull()
    }

    fun search(): Mono<EventSearchResponse> {
        println("service -> client called ${Thread.currentThread().name}")
        return eventClient.getEvent()
    }

    suspend fun searchUsingCoroutine(): EventSearchResponse {
        println("service -> client called ${Thread.currentThread().name}")
        return  eventClient.getEvent().awaitSingle()
    }
}