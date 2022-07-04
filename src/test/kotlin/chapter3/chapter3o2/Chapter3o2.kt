package chapter3.chapter3o2

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

internal class Chapter3o2 : StringSpec({
    /** 사용자 정의 획득자와 설정자 생성하기
     * 코틀린은 기본적으로 public이다.
     */

    "파생 속성을 위한 사용자 정의 획득자 호출" {
        val lowTask = Task("코틀린 학습하기", 2)
        lowTask.isLowPriority shouldBe true

        val nonLowTask = Task("코틀린 학습하기", 4)
        nonLowTask.isLowPriority shouldBe false
    }
})