package cookbook.chapter3

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldBeInstanceOf

internal class Chapter3o9 : StringSpec({
    "3-33. 3의 나머지 연산" {
        for (n in 1..10) {
            val x = when (n % 3) {
                0 -> "$n % 3 == 0"
                1 -> "$n % 3 == 1"
                2 -> "$n % 3 == 2"
                else -> throw Exception("발생할 수 없음...")
            }
            println(x)
            x.shouldBeInstanceOf<String>()
        }
    }
})