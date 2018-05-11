package com.example.administrator.test_kotlin;

import android.annotation.SuppressLint;

import io.reactivex.Observable;     // RxJava 2의 기본 패키지 이름(io.reactivex)
import io.reactivex.Single;

public class RxJavaTest {
    @SuppressLint("CheckResult")
    private void emit() {       // emit(어떤 것을 내보내다)는 Observable 이 subscribe() 함수를 호출한 구독자에게 데이터를 발행하는 것을 표현하는 용어.
        Observable      // Observable class는 데이터의 변화가 발생하는 데이터 소스(180509에서 설명했던 개별적인 월간 매출액 데이터에 해당)
                .just("Hello", "RxJava 2!!")        // Observable의 just() 함수는 가장 단순한 Observable 선언 방식.
                .subscribe(System.out::println);    // Observable 을 구독하기 위한 subscribe() 함수. 이를 호출해야 변화한 데이터를 구독자에게 발행. => 옵서버 패턴과 동일.
                                                    // 반드시 데이터를 수신할 구독자가 subscribe() 함수를 호출해야 Observable 에서 데이터가 발행

        Observable
                .just(1, 2, 3, 4, 5, 6)
                .subscribe(System.out::println);

        Single<String> source = Single.just("Hello Single");
        source.subscribe(System.out::println);

        Single.just("Single Hi").subscribe(System.out::println);
    }

    public RxJavaTest() {
        emit();
    }
}
