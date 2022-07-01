package chapter

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.types.shouldBeInstanceOf


internal class Chapter2o4 : StringSpec({
    /* 사용 가능한 타입 변환 메소드
     * toLong()
     * toByte()
     * toChar()
     * toShort()
     * toInt()
     * toFloat()
     * toDouble()
     */
    "2-24.코틀린에서 Int를 Long으로 승격시키기" {
        val intVar: Int = 3
//        val longVar: Long = intVar  // 컴파일되지 않음
        val longVar = intVar.toLong() // 명시적 타입 변환
        longVar.shouldBeInstanceOf<Long>()
    }
})