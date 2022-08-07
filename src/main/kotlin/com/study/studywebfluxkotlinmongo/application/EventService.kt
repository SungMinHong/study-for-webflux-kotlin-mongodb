package com.study.studywebfluxkotlinmongo.application

import com.study.studywebfluxkotlinmongo.domain.Event
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import com.study.studywebfluxkotlinmongo.infra.EventClient
import com.study.studywebfluxkotlinmongo.infra.EventSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
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

    suspend fun getAllUsingCoroutine(): Flow<Event> {
        println("(1) service -> client called ${Thread.currentThread().name}")
        val eventRes1 = eventClient.getEvent().awaitSingle()
        println(eventRes1.luckyToday.totalPage)

        println("(2) service -> client called ${Thread.currentThread().name}")
        val eventRes2 = eventClient.getEvent().awaitSingle()
        println(eventRes2.luckyToday.totalPage)

        println("service -> repository called ${Thread.currentThread().name}")
        val result = eventRepository.findAll().asFlow()
        println("service -> repository call completed ${Thread.currentThread().name}")
        return result
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
        return eventClient.getEvent().awaitSingle()
    }

    // 스레드 확인용 함수들
    fun callMongoMonoMap(): Mono<Event> {
        println("(1) before call repository Thread: {${Thread.currentThread().name}}")
        return eventRepository.findById("8942c629-e8ad-498b-8835-d728c5a6689d")
            .map {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                it
            }
    }

    fun callMongoMonoFlatMap(): Mono<Event> {
        println("(1) before call repository Thread: {${Thread.currentThread().name}}")
        return eventRepository.findById("8942c629-e8ad-498b-8835-d728c5a6689d")
            .flatMap {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                Mono.just(it)
            }
    }

    fun callMongoFluxMap(): Flux<Event> {
        println("(1) before call repository Thread: {${Thread.currentThread().name}}")
        return eventRepository.findAll()
            .map {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                it
            }
    }

    fun callMongoFluxFlatMap(): Flux<Event> {
        println("(1) before call repository Thread: {${Thread.currentThread().name}}")
        return eventRepository.findAll()
            .flatMap {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                Flux.just(it)
            }
    }

    fun callMongoFluxNestedFlatMap(): Flux<Event> {
        println("(1) before call repository Thread: {${Thread.currentThread().name}}")
        return eventRepository.findAll()
            .flatMap {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                Flux.just(it)
            }
            .flatMap {
                println("(3) repository called. Thread: {${Thread.currentThread().name}}")
                eventRepository.findAll()
            }
    }

    fun callWebClientMonoMap(): Mono<EventSearchResponse> {
        println("(1) before call webClient Thread: {${Thread.currentThread().name}}")
        return eventClient.getEvent()
            .map {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                it
            }
    }

    fun callWebClientMonoFlatMap(): Mono<EventSearchResponse> {
        println("(1) before call webClient Thread: {${Thread.currentThread().name}}")
        return eventClient.getEvent()
            .flatMap {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                Mono.just(it)
            }
    }

    fun callWebClientFluxMap(): Flux<EventSearchResponse> {
        println("(1) before call webClient Thread: {${Thread.currentThread().name}}")
        return eventClient.getEvent().toFlux()
            .map {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                it
            }
    }

    fun callWebClientFluxFlatMap(): Flux<EventSearchResponse> {
        println("(1) before call webClient Thread: {${Thread.currentThread().name}}")
        return eventClient.getEvent().toFlux()
            .flatMap {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                Flux.just(it)
            }
    }

    fun callWebClientFluxNestedFlatMap(): Flux<EventSearchResponse> {
        println("(1) before call webClient Thread: {${Thread.currentThread().name}}")
        return eventClient.getEvent().toFlux()
            .flatMap {
                println("(2) repository called. Thread: {${Thread.currentThread().name}}")
                Flux.just(it)
            }.flatMap {
                println("(3) repository called. Thread: {${Thread.currentThread().name}}")
                eventClient.getEvent()
            }
    }
}