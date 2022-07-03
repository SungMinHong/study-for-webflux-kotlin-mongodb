package chapter2.chapter2o3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

class JavaTest {
    // 2-14. 자바에서 addProduct 함수 호출하기
    @Test
    void supplyAllArguments() {
        System.out.println(OverloadKt.addProduct("Name", 5.0, "Desc"));
    }

    // 2-15. 자바에서 함수 중복 호출하기
    @Test
    void checkOverloads() {
        assertAll("overloads called from Java",
                () -> System.out.println(OverloadKt.addProduct("Name", 5.0, "Desc")),
                () -> System.out.println(OverloadKt.addProduct("Name", 5.0)),
                () -> System.out.println(OverloadKt.addProduct("Name")));
    }
}
