package reactor;

import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

    @Override
    public void hookOnSubscribe(@NotNull Subscription subscription) {
        System.out.println("hook: Subscribed");
        request(1);
    }

    @Override
    public void hookOnNext(@NotNull T value) {
        System.out.println(value);
        request(1);
    }
}