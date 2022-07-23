package reactor;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @DisplayName("Backpressure 예시")
    @Nested
    class BackpressureExample {
        @Test
        void BaseSubscriber의_hookOnSubscribe_재정의_해서_subscribe() {
            Flux.range(1, 16)
                    .subscribe(new BaseSubscriber<>() {
                        final int requestCount = 5; // 5개씩 요청
                        int count = 0;

                        @Override
                        public void hookOnSubscribe(@NotNull Subscription subscription) {
                            System.out.printf("%d회 가져오기: ", count / 5 + 1);
                            request(requestCount);
                        }

                        @Override
                        public void hookOnNext(@NotNull Integer integer) {
                            System.out.print(" " + integer);
                            count++;
                            // 5개 이상 처리가 된 경우 또 5개 요청하기
                            if (count % requestCount == 0) {
                                request(requestCount);
                                System.out.printf("\n%d회 가져오기: ", count / 5 + 1);
                            }
                        }
                    });
        }
    }

    @DisplayName("Error Handling 예시")
    @Nested
    class ErrorHandlingExample {
        public String doSomethingDangerous(final int value) {
            if (value > 3) {
                throw new IllegalArgumentException("3보다 크면 노노!");
            }
            return value + "";
        }

        public String doSecondTransform(final String value) {
            return value + "!";
        }

        @Test
        void 기본_예제() {
            Flux<String> s = Flux.range(1, 10)
                    .map(this::doSomethingDangerous) // 예외를 발생시킬 수 있는 변환을 시도한다.
                    .map(this::doSecondTransform); // 문제없이 실행됐다면 두 번쨰 변환을 시도한다.
            s.subscribe(value -> System.out.println("RECEIVED " + value), // 변환한 값들을 각각 출력한다.
                    error -> System.err.println("CAUGHT " + error) // 에러가 발생했다면 시퀀스를 종료하고 에러 메시지를 출력한다.
            );
        }

        @Test
        void static_fallback_value() {
            Flux.just(1, 2, 4, 3)
                    .map(this::doSomethingDangerous)
                    .onErrorReturn("RECOVERED") // 에러가 발생했다면 시퀀스를 종료하고 에러 메시지를 출력한다.
                    .subscribe(System.out::println);
        }

        @Test
        void static_fallback_value_with_predicate() {
            Flux.just(1, 2, 4, 3)
                    .map(this::doSomethingDangerous)
                    .onErrorReturn(e -> e.getMessage().equals("3보다 크면 노노!"), "RECOVERED") // 에러가 발생했다면 시퀀스를 종료하고 에러 메시지를 출력한다.
                    .subscribe(System.out::println);
        }

        @Test
        void static_fallback_value_with_two_onErrorReturn() {
            Flux.just(1, 2, 4, 3)
                    .map(this::doSomethingDangerous)
                    .onErrorReturn(e -> e.getMessage().equals("3보다 크면 노노!"), "RECOVERED1")
                    .onErrorReturn("RECOVERED2") // 처음 선언한 onErrorReturn이 먼저 실행됨. 만약 위 조건에 해당하지 않는다면 RECOVERED2 반환 됨
                    .subscribe(System.out::println);
        }


        private Flux<String> getFromCache(String k) {
            return Flux.just("[cache] key: " + k, k, k, k);
        }

        private Flux<String> callExternalService(String k) {
            if (k.equals("key2")) {
                return Flux.error(new IllegalArgumentException("외부 호출 실패!"));
            }
            return Flux.just("[external] key: " + k, k, k, k);
        }

        @Test
        void onErrorResume() {
            Flux.just("key1", "key2", "key3")
                    .flatMap(k -> callExternalService(k)
                            .onErrorResume(e -> getFromCache(k)))
                    .onErrorResume(IllegalArgumentException.class, e -> getFromCache("resume key")) // key3도 실행됨
                    .subscribe(System.out::println);
        }

        @Test
        void catchAndRethrow() {
            Flux.just("key1", "key2")
                    .flatMap(this::callExternalService)
                    .onErrorMap(original -> new RuntimeException("oops, SLA exceeded", original))
                    .subscribe(System.out::println);
        }
    }

    @DisplayName("log or react on the side 예시")
    @Nested
    class LoggingExample {
        /**
         * 에러를 계속 전파하면서 시퀀스를 수정하지 않는 다른 일을 하고 싶다면 doOnError 연산자를 사용하세요.
         */

        private Flux<String> callExternalService(String k) {
            if (k.equals("key2")) {
                return Flux.error(new IllegalArgumentException("외부 호출 실패!"));
            }
            return Flux.just("[external] key: " + k, k, k, k);
        }

        @Test
        void doOnError_test() {
            Flux.just("key1", "key2")
                    .flatMap(k -> callExternalService(k)
                            .doOnError(e -> {
                                System.out.println("uh oh, falling back, service failed for key " + k); // 로깅
                            })
                    ).subscribe();
        }
    }


    public static class SomeAutoCloseable implements Closeable {
        @Override
        public void close() throws IOException {
            System.out.println("close 했다!");
        }

        @Override
        public String toString() {
            return "사용했다!";
        }
    }

    @DisplayName("using resources and the finally block 예시")
    @Nested
    class FinallyBlockExample {
        /**
         * 에러를 계속 전파하면서 시퀀스를 수정하지 않는 다른 일을 하고 싶다면 doOnError 연산자를 사용하세요.
         */

        private Flux<String> callExternalService(String k) {
            if (k.equals("key2")) {
                throw new IllegalArgumentException("외부 호출 실패!");
            }
            return Flux.just("[external] key: " + k, k, k, k);
        }

        @Test
        void try_with_resource_test() {
            try (SomeAutoCloseable disposableInstance = new SomeAutoCloseable()) {
                System.out.println(disposableInstance);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Test
        void doFinally_test() {
            Flux<String> flux =
                    Flux.just("foo", "bar", "boo")
                            .doOnSubscribe(s -> System.out.println("doOnSubscribe!"))
                            .doFinally(type -> System.out.println("doFinally!"))
                            .take(2);
            flux.subscribe(System.out::println);
        }

        @Test
        void resourceCleanup_test() {
            AtomicBoolean isDisposed = new AtomicBoolean();
            Disposable disposableInstance = new Disposable() {
                @Override
                public void dispose() {
                    System.out.println("딱 한번만 dispose 실행됨");
                    isDisposed.set(true);
                }

                @Override
                public String toString() {
                    return "DISPOSABLE";
                }
            };
            Flux<String> flux =
                    Flux.using(
                            () -> disposableInstance, // 리소스를 생성한다.
                            disposable -> Flux.just(disposable.toString(), disposable.toString(), disposable.toString()), // Flux<String>를 리턴한다
                            Disposable::dispose // Flux가 종료되거나 취소됐을 때 호출한다.
                    );
            System.out.println("실행 전: " + isDisposed);
            flux.subscribe();
            System.out.println("실행 후: " + isDisposed);
        }
    }

    @Test
    void flux_interval() throws InterruptedException {
        Flux<String> flux = Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 3) {
                        return "tick " + input;
                    }
                    throw new RuntimeException("boom");
                }).onErrorReturn("Uh oh");
        flux.subscribe(System.out::println);
        Thread.sleep(1100); // interval은 timer scheduler 에서 실행한다. 어플리케이션이 값을 생성하지 않은 채로 종료되지 않도록 sleep을 사용한다.
    }

    @Test
    void retrying() throws InterruptedException {
        Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 3) return "tick " + input;
                    throw new RuntimeException("boom");
                })
                .retry(3)
                .elapsed() // 데이터와 이전 데이터를 방출한 이후 소요된 시간을 한군데 묶는다
                .subscribe(System.out::println, error -> System.out.println("에러발생: " + error)); // onError 신호도 확인할 수 있다.
        // 재시도 이후에도 실패했으니 에러가 반환되고 끝남.
        Thread.sleep(2100);
    }

    /** 재시도 사이클은 다음처럼 진행된다.
     * 1. 에러가 발생할 때마다 (재시도 가능성이 있는) 사용자 함수로 장식한 companion Flux로 RetrySignal이 전달된다.
     *    이때 Flux로 지금까지의 모든 재시도 내역을 한눈에 볼 수있다. 이 RetrySignal로 발생한 에러와 메타 데이터에 접근할 수 있다.
     * 2. companion Flux에서 값을 방출하면 재시도한다.
     * 3. companion Flux가 완료되면, 에러는 삼켜지고 재시도 사이클은 중단되며, 그 결과 시퀀스도 완료된다.
     * 4. companion Flux가 에러를 생산하면 (e), 재시도 사이클은 중단하고 결과 시퀀스는 e 에러로 끝난다.
     */
    @Test
    void flux_retry_3times() {
        Flux<String> flux = Flux
                .<String>error(new IllegalArgumentException())  // 계속해서 에러를 생산해 재시도를 유도한다.
                .doOnError(System.out::println) //재시도 전 doOnError에서 모든 에러를 로깅한다.
                .retryWhen(Retry.from(companion ->  //Retry는 매우 간단한 Function 람다를 받는다.
                        companion.take(3)));    //에러 세 번째까지는 재시도하고 이후는 그만두게 설정한다.
        flux.subscribe();
    }
}
