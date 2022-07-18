package com.study.studywebfluxkotlinmongo.infra


data class EventSearchResponse(val luckyToday: LuckyToday) {
    data class LuckyToday(val totalPage: Int, val items: List<Any>)
}

