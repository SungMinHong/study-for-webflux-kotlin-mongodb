package com.study.studywebfluxkotlinmongo.domain

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

//TODO:: ReactiveMongoRepository 사용시 스레드가 변경됨 <- 뭐 떄문이지..?
interface EventRepository : ReactiveMongoRepository<Event, String> {
}