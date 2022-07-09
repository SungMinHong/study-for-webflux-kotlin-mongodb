package chapter3.chapter3o2

import chapter3.chapter3o6.LateInitDemo
import io.kotest.core.spec.style.FreeSpec

internal class Chapter3o6 : FreeSpec({
    /** 나중 초기화를 위해 lateinit 사용하기
     * 이 테크닉은 꼭 필요한 경우만 사용하자. 가능하다면 지연 평가 같은 대안을 먼저 고려하자.
     *
     * 널 비허용으로 선언된 클래스 속성은 생성자에게 초기화되어야 한다.
     * 하지만 가끔은 속성에 할당할 값의 정보가 충분하지 않은 경우가 있다.
     * 이것은 모든 객체가 생성될 때까지 의존성 주입이 일어나지 않는 의존성 주입 프레임워크에서 발생하거나 유닛 테스트의 설정 메소드 안에서 발생한다.
     * 이러한 경우를 대비해 속성에 lateinit 변경자를 사용한다.
     */

    /**
     * lateinit과 lazy의 차이
     * lateinit은 var 속성에만 적용할 수 있다. 반면에 lazy는 val 속성에 사용할 수 있다.
     * lateinit 속성은 속성에 접근할 수 있는 모든 곳에서 초기화할 수 있기 떄문에 객체 바깥쪽에서도 초기화할 수 있다.
     */
    "LateInitDemo test" {
        val lateInitDemo = LateInitDemo()
        lateInitDemo.initializeName()
    }
})