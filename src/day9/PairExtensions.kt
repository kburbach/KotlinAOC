package day9

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Extension function to treat Pair<Int, Int> like coordinates and find the distance between two
 * points
 */
internal fun Pair<Int, Int>.distanceBetween(other: Pair<Int, Int>) =
    sqrt((other.first - first).toFloat().pow(2) + (other.second - second).toFloat().pow(2))

/**
 * Extension function to treat Pair<Int, Int> like coordinates and allow them to be added together
 * i.e. Pair(0, 1) + Pair(1, 2) = Pair(1, 3)
 */
internal operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    Pair(first + other.first, second + other.second)

/**
 * Compares the vertical distance between two points to the horizontal distance between two points
 * @return an Int > 0 for a greater vertical distance, < 0 for a greater horizontal distance and
 *         0 for an equal horizontal and vertical distance i.e. (0, 0) and (2, 2)
 */
internal operator fun Pair<Int, Int>.compareTo(other: Pair<Int, Int>) =
    abs(other.second - second) - abs(other.first - first)