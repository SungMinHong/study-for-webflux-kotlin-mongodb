package chapter3.chapter3o2


import cookbook.chapter3.chapter3o4.Customer
import cookbook.chapter3.chapter3o4.LazyCustomer
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

internal class Chapter3o4 : FreeSpec({
    /** 지원 속성 기법 */

    "3-11. 고객 메시지에 접근하기" - {
        "load messages" - {
            val customer = Customer("Fred").apply { messages } // messages를 처음 로딩
            customer.messages.size shouldBe 3 // messages에 다시 접근. 이미 로딩되어 있음
        }

        "load messages using 'by lazy' keyword" - {
            val customer = LazyCustomer("Fred") // .apply { messages }를 호출하더라도 messages를 로딩하지 않음
            customer.messages.size shouldBe 3 // messages에 접근. 이때 로딩
        }
    }
})