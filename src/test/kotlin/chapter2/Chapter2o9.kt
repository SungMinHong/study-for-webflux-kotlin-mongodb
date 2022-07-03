package chapter2

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.maps.shouldContainValue
import io.kotest.matchers.maps.shouldHaveKey
import io.kotest.matchers.shouldBe

internal class Chapter2o9 : FreeSpec({
    /** to로 Pair 인스턴스 생성하기 */

    "2-40. mapOf의 인자인 pair를 생성하기 위해 to 함수 사용하기" - {
        "create map using infix to function" - {
            val map = mapOf("a" to 1, "b" to 2, "c" to 2)
            map shouldHaveKey "a"
            map shouldHaveKey "b"
            map shouldHaveKey "c"
            map shouldContainValue 1
            map shouldContainValue 2
        }
        "create a Pair from constructor vs to function" - {
            val p1 = Pair("a", 1)
            val p2 = "a" to 1

            p1.first shouldBe "a"
            p1.second shouldBe 1
            p2.first shouldBe "a"
            p2.second shouldBe 1
            p1 shouldBe p2
        }
    }

    "2-41. Pair 클래스 구조 분해" - {
        "destructing a Pair" - {
            val pair = "a" to 1
            val (x, y) = pair

            x shouldBe "a"
            y shouldBe 1
        }
    }

    // +) 3개의 값을 나타내는 Triple이라는 이름의 클래스가 코틀린 표준 라이브러리에 들어 있다.
})