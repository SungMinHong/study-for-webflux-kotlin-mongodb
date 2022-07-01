package chapter

import io.kotest.core.spec.style.StringSpec


internal class Chapter2o5 : StringSpec({
    "2-25.42를 이진법으로 출력하기" {
        println(42.toString(2))
    }

    "2-26. 숫자 42를 사용 가능한 모든 기수로 출력" {
        (Character.MIN_RADIX..Character.MAX_RADIX).forEach {
            radix -> println("$radix: ${42.toString(radix)}")
        }
    }
})