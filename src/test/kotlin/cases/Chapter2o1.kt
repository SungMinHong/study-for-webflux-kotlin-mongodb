package cases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf


internal class Chapter2o1 : StringSpec({
    class Person(
        val first: String,
        val middle: String?,
        val last: String
    )

    "2-1. null 할당이 불가능한 변수 선언" {
        var name: String
        name = "mina" // 널이 아닌 문자열을 할당
        // name = null // 불가
    }

    "2-2. null 허용 변수 선언" {
        val hongSungMin = Person("SungMin", null, "Hong")
        val hongArin = Person("Arin", null, "Hong")
        val jacquesDominiqueWilkins = Person("Jacques", "Dominique", "Wilkins")
    }

    "2-3. val 변수의 null 허용성 검사하기" {
        val person = Person(first = "SungMin", middle = null, last = "Hong")
        if (person.middle != null) {
            val middleNameLength = person.middle.length // null 할당이 불가능한 문자열 타입으로 영리한 타입 변환(smart cast)을 수행
        }
    }

    "2-4. var 변수가 null 값이 아님을 단언하기" {
        var person = Person("North", "South", "West")
        if (person.middle != null) {
//            val middleNameLength = person.middle.length // 복합식(complex expression)이므로 String 타입으로 영리한 타입 변환이 불가능하다.
            val middleNameLength = person.middle!!.length // null이 아님을 단언(assert) 꼭 필요한 경우가 아니라면 사용하지 말 것. NPE 가능
        }
    }

    "2-4-1. var 변수가 null 값이 아님을 단언했지만 null인 경우 NPE 발생" {
        var person = Person("North", "South", "West")
        if (person.middle != null) {
            person = Person("North", null, "West")
            shouldThrow<NullPointerException> { person.middle!!.length }
        }
    }

    "2-5. 안전 호출 연산자 사용하기" {
        val person = Person("North", null, "West")
        val middleNameLength = person.middle?.length // 안전 호출. 결과 타입은 Int?이다.
        middleNameLength shouldBe null
    }

    "2-6. 안전 호출 연산자와 엘비스 연산자" {
        val person = Person("North", null, "West")
        val middleNameLength = person.middle?.length ?: 0 // middle이 null일 꼉우 엘비스 연산자는 0을 리턴한다.
        middleNameLength.shouldBeInstanceOf<Int>()
    }

    "2-7. 안전 타입 변환 연산자" {
        val person1 = Person("North", null, "West")
        val person2 = person1 as? Person // 변수 person2의 타입은 Person이다.
        person2.shouldBeInstanceOf<Person>()
    }

    "2-7-1. 안전 타입 변환 연산자: null인 경우" {
        val personNull = null
        val person2 = personNull as? Person // 변수 person2는 null 이다.
        person2 shouldBe null
    }
})