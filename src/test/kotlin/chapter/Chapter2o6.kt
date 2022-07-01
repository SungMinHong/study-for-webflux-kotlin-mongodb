package chapter

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.math.pow


internal class Chapter2o6 : StringSpec({
    "2-28. 정수를 지수로 만들기" {
        2.toDouble().pow(8).toInt() shouldBe 256
    }

    /* "2-29. 거듭제곱 계산을 위한 중위 연산자 infix 정의하기"
    * Infix 함수는 두개의 변수 가운데 오는 함수를 말합니다. 코틀린에서 기본적으로 정의된 Infix 함수들 중에 Pair를 만드는 to가 있습니다.
    * https://codechacha.com/ko/kotlin-infix-functions/
     */
    infix fun Int.`**`(x: Int) = toDouble().pow(x).toInt()
    infix fun Long.`**`(x: Int) = toDouble().pow(x).toLong()
    infix fun Float.`**`(x: Int) = pow(x)
    infix fun Double.`**`(x: Int) = pow(x)

    fun Int.pow(x: Int) = `**`(x)
    fun Long.pow(x: Int) = `**`(x)


    "예제 2-30. 위에서 정희한 **확장 함수 사용하기" {
        2 `**` 0 shouldBe 1
        2 `**` 1 shouldBe 2
        2 `**` 2 shouldBe 4
        2 `**` 3 shouldBe 8

        2L `**` 0 shouldBe 1L
        2L `**` 1 shouldBe 2L
        2L `**` 2 shouldBe 4L
        2L `**` 3 shouldBe 8L

        2F `**` 0 shouldBe 1F
        2F `**` 1 shouldBe 2F
        2F `**` 2 shouldBe 4F
        2F `**` 3 shouldBe 8F

        2.0 `**` 0 shouldBe 1.0
        2.0 `**` 1 shouldBe 2.0
        2.0 `**` 2 shouldBe 4.0
        2.0 `**` 3 shouldBe 8.0

        2.pow(0) shouldBe 1
        2.pow(1) shouldBe 2
        2.pow(2) shouldBe 4
        2.pow(3) shouldBe 8

        2L.pow(0) shouldBe 1L
        2L.pow(1) shouldBe 2L
        2L.pow(2) shouldBe 4L
        2L.pow(3) shouldBe 8L
    }

})