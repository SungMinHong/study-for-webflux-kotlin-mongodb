package chapter3.chapter3o2

import chapter3.chapter3o3.OrderItem
import chapter3.chapter3o3.Product
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.number.IsCloseTo.closeTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertAll

internal class Chapter3o3 : FreeSpec({
    /** 데이터 클래스 정의하기
     * equals, hashCode, toString 등이 완벽하게 갖춰진 엔티티를 나타내는 클래스를 생성하고 싶은 경우 data 키워드를 사용한다.
     */

    "3-5. 코틀린이 생성한 equals와 hashCode 구현 사용하기" - {
        "check equivalence" - {
            val p1 = Product("baseball", 10.0)
            val p2 = Product("baseball", 10.0, false)

            p1 shouldBe p2
            p1.hashCode() shouldBe p2.hashCode()
        }

        "create set to check equals and hashcode" - {
            val p1 = Product("baseball", 10.0)
            val p2 = Product(name = "baseball", onSale = false, price = 10.0)

            val products = setOf(p1, p2)
            products.size shouldBe 1
        }
    }

    "3-6. copy 함수 테스트" {
        val p1 = Product("baseball", 10.0)
        val p2 = p1.copy(price = 12.0)
        p2.name shouldBe "baseball"
        p2.price shouldBe 12.0
        p2.onSale shouldBe false
    }

    "3-8. 얕은 복사를 검증하는 테스트" {
        val item1 = OrderItem(Product("baseball", 10.0), 5)
        val item2 = item1.copy()

        assertAll(
            { assertTrue(item1 == item2) },
            { assertFalse(item1 === item2) }, // copy 함수로 생성한 OrderItem은 다른 객체다.
            { assertTrue(item1.product == item2.product) },
            { assertTrue(item1.product === item2.product) } //두 OrderItem 인스턴스에 있는 Product는 같은 객체다.
        )
    }

    "3-9. Product 인스턴스 구조 분해" {
        val p = Product("baseball", 10.0)

        val (name, price, sale) = p
        assertAll(
            { assertEquals(p.name, name) },
            { assertThat(p.price, `is`(closeTo(price, 0.01))) },
            { assertFalse(sale) }
        )
    }
})