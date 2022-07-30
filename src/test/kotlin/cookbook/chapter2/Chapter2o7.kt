package cookbook.chapter2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.ints.shouldNotBeInRange
import io.kotest.matchers.shouldBe


internal class Chapter2o7 : StringSpec({
    /* 코틀린은 확장 함수로서 다음 시프트 연산자를 Int와 Long에 정의해놓았다.
    * shl: 부호 있는 왼쪽 시프트 Signed left shift
    * shr: 부호 있는 오른쪽 시프트 Signed right shift
    * ushr: 부호 없는 오른쪽 시프트 Unsigned right shift
     */
    "2-31. 2를 곱하거나 나누기" {
        // left shifts doubling from 1
        1 shl 1 shouldBe 2  // 0000 0010
        1 shl 2 shouldBe 4  // 0000 0100
        1 shl 3 shouldBe 8  // 0000 1000
        1 shl 4 shouldBe 16 // 0001 0000
        1 shl 5 shouldBe 32 // 0010 0000
        1 shl 6 shouldBe 64 // 0100 0000
        1 shl 7 shouldBe 128    // 1000 0000

        // right shifts halving from 235
        // 235:                    1110 1011
        235 shr 1 shouldBe 117  // 0111 0101
    }

    /* 부호를 보존하지 않고 값을 시프트하려는 경우 ushr 함수를 사용하면 된다.
     * shr과 ushr 함수를 양수인 경우에는 똑같은 동작을 하지만 음수인 경우에는 shr이 왼쪽에서부터 1을 채우기 때문에 결과는 여전히 음수다.
     */
    "2-32. ushr 함수 사용과 shr 함수 사용 비교" {
        val n1 = 5
        val n2 = -5
        println(n1.toString(2)) // 101
        println(n2.toString(2)) // -101

        n1 shr 1 shouldBe 2
        n1 ushr 1 shouldBe 2

        n2 shr 1 shouldBe -3
        n2 ushr 1 shouldBe 2_147_483_645
    }

    "2-33 큰 정수 2개의 중간 값 찾기" {
        val high = (0.99 * Int.MAX_VALUE).toInt()
        val low = (0.75 * Int.MAX_VALUE).toInt()

        val mid1 = (high + low) / 2
        val mid2 = (high + low) ushr 1

        mid1 shouldNotBeInRange  low..high
        mid2 shouldBeInRange low..high
    }
})