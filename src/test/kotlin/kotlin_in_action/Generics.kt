package kotlin_in_action

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Generics {
    /** T가 컴파일 이후 사라지기 때문에 다음 함수는 컴파일 오류 발생
     * fun <T> isA(value: Any) = value is T
     */

    // inline과 reified를 사용하면 가능
    inline fun <reified T> isA(value: Any) = value is T

    @Test
    fun isA_test() {
        var result = isA<String>("abc")
        assertThat(result).isTrue
        assertThat(isA<String>(1)).isFalse
    }
}