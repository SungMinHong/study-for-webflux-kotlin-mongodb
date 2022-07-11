package reactor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

class FluxMonoSimpleTest {
    @Test
    void create_flux() {
        Flux<String> seq1 = Flux.just("foo1", "bar1", "foobar1");
        List<String> list = Arrays.asList("foo2", "bar2", "foobar2");
        Flux<String> seq2 = Flux.fromIterable(list);
    }

    @Test
    void create_mono_using_factory_method() {
        Mono<String> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
        data.subscribe(System.out::println);
        numbersFromFiveToSeven.subscribe(System.out::println);
    }


    @DisplayName("subscribe method 예시")
    @Nested
    class SubscribeMethodExample {
        @Test
        void 구독자_테스트() {
            Flux<Integer> ints = Flux.range(1, 3); // 구독자가 생기면 값 3개를 생성하는 Flux를 세팅한다
            // 구독자가 2개 이니 1~3까지 2번 생성한다.
            ints.subscribe(System.out::println);
            ints.subscribe(System.out::println);
        }

        @Test
        void 에러_처리() {
            Flux<Integer> ints = Flux.range(1, 4) // 구독자가 생기면 값 4개를 생성하는 Flux를 세팅한다
                    .map(i -> {
                        if (i <= 3) {
                            return i;   // 1~3은 값을 리턴
                        }
                        throw new RuntimeException("Got to 4"); // 예외 반환
                    });
            ints.subscribe(System.out::println, // 출력
                    error -> System.err.println("Error: " + error)  // 반환딘 예외 출력
            );
        }

        @Test
        void 완료_처리() {
            Flux<Integer> ints = Flux.range(1, 4); // 구독자가 생기면 값 4개를 생성하는 Flux를 세팅한다
            ints.subscribe(System.out::println,
                    error -> System.err.println("Error " + error),
                    () -> System.out.println("Done(완료)")); // 완료 이벤트 핸들러를 가지고 있는 구독자로 구독한다.
            // 에러 신호와 완료 신호는 모두 종료 이벤트이며, 상호 배타적이다(둘다 받을 수는 없다). 완료 컨슈머가 잘 동작하려면 에러가 발생하지 않도록 주의해야 한다.
        }
    }

    @DisplayName("BaseSubscriber 예시")
    @Nested
    class BaseSubscriberExample {
        @Test
        void SampleSubscriber_생성() {
            var sampleSubscriber = new SampleSubscriber<Integer>();
            var ints = Flux.range(1, 4);
            ints.subscribe(System.out::println,
                    error -> System.err.println("Error " + error),
                    () -> System.out.println("Done"));
            ints.subscribe(sampleSubscriber);
        }
    }
}
