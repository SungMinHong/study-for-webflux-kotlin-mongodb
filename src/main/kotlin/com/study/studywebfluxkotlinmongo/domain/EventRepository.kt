package com.study.studywebfluxkotlinmongo.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface EventRepository : ReactiveMongoRepository<Event, String> {
}