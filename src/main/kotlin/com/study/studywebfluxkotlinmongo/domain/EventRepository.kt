package com.study.studywebfluxkotlinmongo.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface EventRepository : ReactiveMongoRepository<Event, String> {
}