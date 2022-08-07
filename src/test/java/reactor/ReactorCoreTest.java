package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class ReactorCoreTest {
    @Test
    void testAppendBoomError() {
        Flux<String> source = Flux.just("thing1", "thing2");

        StepVerifier.create(appendBoomError(source))
                .expectNext("thing1")
                .expectNext("thing2")
                .expectErrorMessage("boom")
                .verify();
    }

    private <T> Flux<T> appendBoomError(Flux<T> source) {
        return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
    }

    @Test
    void withVirtualTimeTest() {
        StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
                .expectSubscription()
                .expectNoEvent(Duration.ofDays(1))
                .expectNext(0L)
                .verifyComplete();
    }
}
