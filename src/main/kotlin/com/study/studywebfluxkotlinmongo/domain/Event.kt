package com.study.studywebfluxkotlinmongo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "event")
data class Event (
    @Id
    val id: String,
    val name: String
)