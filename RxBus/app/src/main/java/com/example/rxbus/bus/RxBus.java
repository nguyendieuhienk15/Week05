package com.example.rxbus.bus;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus {

    private final PublishSubject<Object> bus = PublishSubject.create();

    public void post(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> getBusObservable() {
        return bus;
    }
}
