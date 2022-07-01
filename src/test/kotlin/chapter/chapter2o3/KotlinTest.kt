package chapter.chapter2o3

import chapter.chapter2o3.OverloadKt.addProduct
import io.kotest.core.spec.style.AnnotationSpec
import org.junit.jupiter.api.assertAll


internal class KotlinTest : AnnotationSpec() {
    // 2-13. 코틀린에서 중복 함수 변형 호출하기
    @Test
    fun check_all_overloads() {
        assertAll("Overloads called from Kotlin",
            { println(addProduct("Name", 5.0, "Desc")) },
            { println(addProduct("Name", 5.0)) },
            { println(addProduct("Name")) }
        )
    }
}