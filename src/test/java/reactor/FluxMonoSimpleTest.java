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
    class subscribeMethodExample {
        @Test
        void 구독자_테스트() {
            Flux<Integer> ints = Flux.range(1, 3); // 구독자가 생기면 값 3개를 생성하는 Flux를 세팅한다
            // 구독자가 2개 이니 1~3까지 2번 생성한다.
            ints.subscribe(System.out::println);
            ints.subscribe(System.out::println);
        }
    }

}
