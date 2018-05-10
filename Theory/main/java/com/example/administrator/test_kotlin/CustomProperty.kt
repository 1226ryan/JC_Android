package com.example.administrator.test_kotlin

import java.util.*

class CustomProperty(val height: Int, val width: Int) {
    // getter 와 setter 에 특정 구현부를 넣고 싶은 경우
    // isSquare란 변수의 getter 을 만듬
    val isSquare: Boolean
        get() {
            return height == width
        }
}

fun createRandomrectangle(): CustomProperty {
    val random = Random()
    return CustomProperty(random.nextInt(), random.nextInt())
}