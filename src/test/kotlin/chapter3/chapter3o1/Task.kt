package chapter3.chapter3o1

class Task(val name: String, _priority: Int = DEFAULT_PRIORITY) {
    /**
     * 코틀린에서 val은 키워드지만 const는 private, inline 등과 같은 변경자임에 유의하자.
     * 그런 이유로 const가 val 키워드를 대체하는 것이 아니라 반드시 같이 쓰여야 한다
     */
    companion object {
        const val MIN_PRIORITY = 1  // 컴파일 타임 상수
        const val MAX_PRIORITY = 5  // 컴파일 타임 상수
        const val DEFAULT_PRIORITY = 3  // 컴파일 타임 상수
    }

    var priority = validPriority(_priority) // 사용자 정의 설정자(setter)를 사용하는 속성
        set(value) {
            field = validPriority(value)
        }

    private fun validPriority(p: Int) = p.coerceIn(MIN_PRIORITY, MAX_PRIORITY) // private 검증 함수
}