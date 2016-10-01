package rxjava;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReactiveExample {

    private static final List<String> INSTRUMENTS = Arrays.asList("");

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Observable<Price> priceObservable = Observable.create(new Observable.OnSubscribe<Price>() {

            @Override
            public void call(Subscriber<? super Price> subscriber) {

                executor.scheduleAtFixedRate(() -> subscriber.onNext(new Price("XS1234567890", "GBP", 101.34)),
                        1, 1, TimeUnit.SECONDS);
            }
        });

        Subscription s = priceObservable.subscribe(price -> {
            System.out.println(price);
        });
    }



    private static class Price {
        String instrument;
        String currency;
        double price;

        Price(String instrument, String currency, double price) {
            this.instrument = instrument;
            this.currency = currency;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Price{" +
                    "instrument='" + instrument + '\'' +
                    ", currency='" + currency + '\'' +
                    ", price=" + price +
                    '}';
        }
    }
}
