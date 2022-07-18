package com.study.studywebfluxkotlinmongo.infra

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class EventClient {
    private val myWebclient = WebClient.builder()
        .baseUrl("https://search.shopping.naver.com")
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .build()

    // webClint 사용시 동일한 스레드에서 계속 처리됨
    fun getEvent(): Mono<EventSearchResponse> =
        myWebclient.get()
            .uri("/api/search/aside?query=이벤트")
            .retrieve()
            .bodyToMono(EventSearchResponse::class.java)
            .flatMap {
                println("EventClient call completed ${Thread.currentThread().name}")
                Mono.just(it)
            }

}