package com.example.administrator.test_kotlin

enum class Color(val r: Int, val g: Int, val b: Int) {
    // enum 사용 시 반드시 마지막에 ";" 를 붙여야함
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b
}

// when 은 object 를 지원
// when 에 enum 을 사용
fun getMnemonic(color: Color) =
        when (color) {
            Color.RED -> "Richard"
            Color.ORANGE -> "Of"
            Color.YELLOW -> "York"
            Color.GREEN -> "Gave"
            Color.BLUE, Color.INDIGO, Color.VIOLET -> "Cold"
        }

// when 인자로 object 사용
fun mix(c1: Color, c2: Color) = when (setOf(c1, c2)) {
    setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
    setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
    setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO
    else -> {
        throw Exception("Dirty color")
    }
}

// when 인자 없이 사용 -> 단! when에 인자가 없다면, 조건부분은 반드시 Boolean을 반환하는 expression이어야 합니다.
fun mixOptimized(c1: Color, c2: Color) =
        when {
            (c1 == Color.RED && c2 == Color.YELLOW) ||
                    (c1 == Color.YELLOW && c2 == Color.RED) ->
                Color.ORANGE

            (c1 == Color.YELLOW && c2 == Color.BLUE) ||
                    (c1 == Color.BLUE && c2 == Color.YELLOW) ->
                Color.GREEN

            (c1 == Color.BLUE && c2 == Color.VIOLET) ||
                    (c1 == Color.VIOLET && c2 == Color.BLUE) ->
                Color.INDIGO

            else -> throw Exception("Dirty color")
        }