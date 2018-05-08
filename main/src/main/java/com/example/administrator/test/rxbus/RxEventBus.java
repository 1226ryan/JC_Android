package com.example.administrator.test.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Administrator on 2018-02-28.
 */

public class RxEventBus {

    private static RxEventBus instance;
    private final PublishSubject<Object> bus = PublishSubject.create();

    private RxEventBus(){}

    public static RxEventBus getInstance() {
        if (instance == null)
            instance = new RxEventBus();

        return instance;
    }

    public Observable<Object> getBusObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }

    public void post(Object o) {
        bus.onNext(o);
    }
}
