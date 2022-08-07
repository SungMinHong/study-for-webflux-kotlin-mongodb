package com.study.studywebfluxkotlinmongo.application

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EventServiceTest {
    @Autowired
    lateinit var eventService: EventService

    /**
     * - 몽고디비 호출 시
     *     - Mono
     *         - map
     *             - 스레드 변경됨
     * (1) before call repository Thread: {main}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     */
    @Test
    fun callMongoMono() {
        val mono = eventService.callMongoMonoMap()
        mono.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - 몽고디비 호출 시
     *     - Mono
     *         - flatMap
     *
     * (1) before call repository Thread: {main}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     */
    @Test
    fun callMongoMonoFlatMap() {
        val mono = eventService.callMongoMonoFlatMap()
        mono.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - 몽고디비 호출 시
     *     - Flux
     *         - flatMap
     *
     * (1) before call repository Thread: {main}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     */
    @Test
    fun callMongoFluxMap() {
        val flux = eventService.callMongoFluxMap()
        flux.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - 몽고디비 호출 시
     *     - Flux
     *         - flatMap
     *
     * (1) before call repository Thread: {main}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     * (2) repository called. Thread: {Thread-8}
     * (3) test instance -> eventService called. Thread: {Thread-8}
     */
    @Test
    fun callMongoFluxFlatMap() {
        val flux = eventService.callMongoFluxFlatMap()
        flux.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - 몽고디비 호출 시
     *     - Flux
     *         - flatMap 중첩
     *
     * (1) before call repository Thread: {main}
     * (2) repository called. Thread: {Thread-8}
     * (3) repository called. Thread: {Thread-8}
     * (4) test instance -> eventService called. Thread: {Thread-11}
     * (4) test instance -> eventService called. Thread: {Thread-16}
     * (4) test instance -> eventService called. Thread: {Thread-17}
     */
    @Test
    fun callMongoFluxNestedFlatMap() {
        val flux = eventService.callMongoFluxNestedFlatMap()
        flux.subscribe {
            println("(4) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - WebClient 호출 시
     *     - Mono
     *         - map
     *
     * (1) before call webClient Thread: {main}
     * (2) repository called. Thread: {reactor-http-nio-2}
     * (3) test instance -> eventService called. Thread: {reactor-http-nio-2}
     */
    @Test
    fun callWebClientMonoMap() {
        val mono = eventService.callWebClientMonoMap()
        mono.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - WebClient 호출 시
     *     - Mono
     *         - flatMap
     *
     * (1) before call webClient Thread: {main}
     * (2) repository called. Thread: {reactor-http-nio-2}
     * (3) test instance -> eventService called. Thread: {reactor-http-nio-2}
     */
    @Test
    fun callWebClientMonoFlatMap() {
        val mono = eventService.callWebClientMonoFlatMap()
        mono.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - WebClient 호출 시
     *     - Flux
     *         - map
     *
     * (1) before call webClient Thread: {main}
     * (2) repository called. Thread: {reactor-http-nio-2}
     * (3) test instance -> eventService called. Thread: {reactor-http-nio-2}
     */
    @Test
    fun callWebClientFluxMap() {
        val flux = eventService.callWebClientFluxMap()
        flux.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - WebClient 호출 시
     *     - Flux
     *         - flatMap
     *
     * (1) before call webClient Thread: {main}
     * (2) repository called. Thread: {reactor-http-nio-2}
     * (3) test instance -> eventService called. Thread: {reactor-http-nio-2}
     */
    @Test
    fun callWebClientFluxFlatMap() {
        val flux = eventService.callWebClientFluxFlatMap()
        flux.subscribe {
            println("(3) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(1000)
    }

    /**
     * - WebClient 호출 시
     *     - Flux
     *         - flatMap 중첩
     * (1) before call webClient Thread: {main}
     * (2) repository called. Thread: {reactor-http-nio-2}
     * (3) repository called. Thread: {reactor-http-nio-2}
     * (4) test instance -> eventService called. Thread: {reactor-http-nio-2}
     */
    @Test
    fun callWebClientFluxNestedFlatMap() {
        val flux = eventService.callWebClientFluxNestedFlatMap()
        flux.subscribe {
            println("(5) test instance -> eventService called. Thread: {${Thread.currentThread().name}}")
            println(it)
        }
        Thread.sleep(2000)
    }

}