package chapter3.chapter3o8

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class Chapter3o8 : StringSpec ({
    "3-27. 코틀린에서 싱글톤 멤버에 접근하기" {
        MySingleton.myFunction() shouldBe "Hello"
        MySingleton.myProperty shouldBe 3
    }
})