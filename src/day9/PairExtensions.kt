package day9

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

internal fun Pair<Int, Int>.distanceBetween(other: Pair<Int, Int>) =
    sqrt((other.first - first).toFloat().pow(2) + (other.second - second).toFloat().pow(2))

internal operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    Pair(first + other.first, second + other.second)


internal operator fun Pair<Int, Int>.compareTo(other: Pair<Int, Int>) =
    abs(other.second - second) - abs(other.first - first)