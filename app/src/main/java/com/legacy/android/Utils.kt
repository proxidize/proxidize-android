package com.legacy.android


fun getRandomPort(from: Int = 4000, to: Int = 60000): Int {
    return ((Math.random() * (to - from)).toInt()) + from
}

fun getAlphaNumericString(length: Int = 4): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}