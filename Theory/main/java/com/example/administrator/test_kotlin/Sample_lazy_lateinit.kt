package com.example.administrator.test_kotlin

class Sample_lazy_lateinit {
    // 호출 시점에 한번 초기화를 진행하고, 그 이후에는 가져다가 쓰기만 하는 것을 확인할 수 있다.
    private val subject: String by lazy {
        println("subject initialized!!!!")
        "Subject Initialized"
    }

    fun test() {
        println("Not Initialized")
        println("subject one : $subject")
        println("subject two : $subject")
        println("subject three : $subject")
    }
}