package com.example.cnwlc.test_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("기초 변수")
        val numbers:MutableList<Int> = mutableListOf(1, 2, 3)
        val readOnly:List<Int> = numbers
        println(numbers)        // [1, 2, 3]
        numbers.add(4)
        println(readOnly)       // [1, 2, 3, 4]
//        readOnly.add(5)         // add 에 빨간 표시로 compile error가 된다.


        println("기초 함수")
        first()                             //  Hello World!
        second("World!")            // Hello World!
        println(third(1, 3))        // 4


        println("조건문")
        println(if_else(2, 3))      // 3
        count(1)                    // There is 1 item.
        count(2)                    // There are 2 items


        println("반복문")
        val items = listOf("Fun", "Var", "Val")
        for_each(items)     // item : Fun //item : Var //item : Val
        _while()            // item : Fun //item : Var //item : Val //item : Item
    }

    fun first(): Unit {
        println("Hello World!")
    }
    fun second(name: String) {
        println("Hello $name")
    }
    fun third(a: Int, b: Int): Int {
        return a + b
    }

    fun if_else(a:Int, b:Int) : Int {
        if(a>b) return a
        else return b
    }
    fun count(count:Int) {
        when(count) {
            1->println("There is $count item.")
            else -> println("There are $count items")
        }
    }

    fun for_each(items:List<String>) {
        for(item in items) {
            println("item : $item")
        }
    }
    fun _while() {
        val items = listOf("Fun", "Var", "Val", "Item")
        var i = 0
        while (i<items.size) {
            println("item : ${items[i]}")
            i++
        }
    }
}
