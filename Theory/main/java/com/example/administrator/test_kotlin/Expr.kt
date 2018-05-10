package com.example.administrator.test_kotlin

// 스마트 캐스트 사용 -> object 타입 확인과 변환을 한번에 해주는 기능
/*
아래 예제를 보면 Num 과 Sum 은 모두 Expr 이라는 interface 를 구현하고 있습니다.
is 는 instanceOf 와 같은 역할이라고 보면 되며 as는 강제 캐스팅이라고 보면 됩니다.

아래에서 if (e is Num) 을 통과하여 내부 블럭으로 들어왔다면 e as Num 을 해줄 필요가 없습니다.
사실 이미 내부 블럭에서 e는 컴파일러에 의해 Num 으로 이미 자동 캐스팅된 상태입니다.
 */

interface Expr

class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left)
    }

    throw IllegalArgumentException("Unknown expression")
}

// 위 코드 when 으로 간소화
fun evalWhen(e: Expr): Int =
        when (e) {
            is Num -> e.value
            is Sum -> evalWhen(e.right) + evalWhen(e.left)
            else -> throw IllegalArgumentException("Unknown expression")
        }


// if문과 when 에서의 블럭 사용
/*
if문과 when문은 블럭을 사용해서 표현할 수 도 있습니다.
이때 **마지막 문장이 블럭 전체의 결과값**이 됩니다!!
 */
fun evalWithLogging(e: Expr): Int =
        when (e) {
            is Num -> {
                println("num: ${e.value}")
                e.value
            }
            is Sum -> {
                val left = evalWithLogging(e.left)
                val right = evalWithLogging(e.right)
                println("sum: $left + $right")
                left + right
            }
            else -> throw IllegalArgumentException("Unknown expression")
        }