package com.study.studywebfluxkotlinmongo.infra

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.kotlin.test.test

internal class EventClientTest {
    private lateinit var eventClient: EventClient

    private var server: MockWebServer? = null

    @BeforeEach
    fun setup() {
        this.server = MockWebServer()
        val baseUrl: String = this.server!!.url("/").toString()
        val myWebclient = WebClient.create(baseUrl)
        this.eventClient = EventClient(myWebclient)
    }

    @AfterEach
    fun shutdown() {
        this.server?.shutdown()
    }

    @Test
    fun getEvent() {
        // given
        this.server!!.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(
                    """
                    {
                      "luckyToday": {
                        "totalPage": 0,
                        "items": []
                      }
                    }
                """.trimIndent()
                )
        )

        // when
        val source = eventClient.getEvent()

        // then
        source.test()
            .assertNext {
                assertThat(it.luckyToday.totalPage).isZero
                assertThat(it.luckyToday.items).isEmpty()
            }.verifyComplete()
    }
}