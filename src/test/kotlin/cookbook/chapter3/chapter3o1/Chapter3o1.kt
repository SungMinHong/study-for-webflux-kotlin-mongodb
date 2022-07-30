package cookbook.chapter3.chapter3o1

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class Chapter3o1 : StringSpec({
    /** const와 val의 차이 이해하기
     * 컴파일 타입 상수에 const 변경자(modifier)를 사용한다.
     * val 키워드는 변수에 한 번 할당되면 변경이 불가능함을 나타내지만 이러한 할당은 실행 시간에 일어난다.
     */

    "Task 생성" {
        val myTask = Task("코틀린 학습하기", 2)
        myTask.priority shouldBe 2

        val myTask2 = Task("코틀린 학습하기", -1)
        myTask2.priority shouldBe Task.MIN_PRIORITY

        val myTask3 = Task("코틀린 학습하기", 9)
        myTask3.priority shouldBe Task.MAX_PRIORITY
    }
})