package com.example.administrator.test_kotlin

data class SampleVal(val name:String, val email:String)
// val 는 불변이므로 읽기 전용이며, 컴파일러가 getter 만 생성

/*
DTO 작성(POJO / POCO)
기본 제공하는 기능
    모든 프로퍼티의 getter(var 의 경우 setter 포함)
    equals()
    hasCode()
    toString()
    copy()
    component1(), component2() ....
 */