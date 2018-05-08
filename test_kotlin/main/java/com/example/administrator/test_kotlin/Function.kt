package com.example.administrator.test_kotlin

fun sum(a: Int, b: Int) = a + b

fun comparison(a: Int, b: Int) = if (a > b) a else b

fun getStringLength(obj: Any): Int? {
    if (obj is String)
        return obj.length   // 'obj' is automatically cast to 'String' int this branch
    else if (obj !is String)
        return null

    return null
}

fun listTest() {
    val arrayList = ArrayList<String>()
    for (s in arrayList) {
        println("string : $s")
    }
}

fun filterTest() {
    val list1 = listOf(1, 3, 5, 7, 9)
    list1.filter { it > 5 }.map { println("index "+(it*2)) }

    // 코틀린 null 처리
    val list2: List<Int?> = listOf(1, 2, null, 3, 4, 5)
    val intList : List<Int> = list2.filterNotNull()
    intList.map { println("NotNull it $it") }
}

fun printProduct(arg1: String, arg2: String) {
    val x = Integer.parseInt(arg1)
    val y = Integer.parseInt(arg2)

    // Using `x * y` yields error because they may hold nulls.
    if (x != null && y != null) {
        // x and y are automatically cast to non-nullable after null check
        println(x * y)
    }
    else {
        println("either '$arg1' or '$arg2' is not a number")
    }
}

fun getListOf() {
    val items = listOf("apple", "banana", "kiwifruit")
    for (item in items) {
        println(item)
    }
    println("---------------------------------------")
    for (index in items.indices) {
        println("item at $index is ${items[index]}")
    }
}

fun getWhile() {
    val items = listOf("apple", "banana", "kiwifruit")
    var index = 0
    while (index < items.size) {
        println("item at $index is ${items[index]}")
        index++
    }
}

fun getWhen() {
    cases("Hello") // String
    cases(1) // Int
    cases(System.currentTimeMillis()) // Long
    cases("hello") // Unknown
}
fun cases(obj: Any) {
    when (obj) {
        1 -> println("One")
        "Hello" -> println("Greeting")
        is Long -> println("Long")
        !is String -> println("Not a string")
        else -> println("Unknown")
    }
}

fun getRanges() {
    val x = 10
    val y = 9
    if (x in 1..y+1) {
        println("fits in range")
    }
    println("---------------------------------------")
    for (z in 1..5) {
        println(z)
    }
    println("---------------------------------------")
    val list = listOf("a", "b", "c")
    if (-1 !in 0..list.lastIndex) {
        println("-1 is out of range")
    }
    if (list.size !in list.indices) {
        println("list size is out of valid list indices range too")
    }
    println("---------------------------------------")
    for (x in 1..10 step 2) {
        print(x)
    }
    println()
    for (x in 9 downTo 0 step 3) {
        print(x)
    }
}

