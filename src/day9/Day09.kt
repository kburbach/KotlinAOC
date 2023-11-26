package day9

import println
import readInput
import day9.Motion.Direction
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

const val debug = false
fun main() {

    val tailPositions =
        mutableSetOf<Pair<Int, Int>>() //set to keep track of [x,y] positions that the tail has visited

    //each knot starts at (0,0)
    val knotPositions = MutableList(10) {
        0 to 0
    }

    readInput("day9/test2").mapNotNull(Motion::tryParseMotion)
        .forEach { motion ->
            "\nMoving ${motion.direction} ${motion.amount}".println(debug)

            for (i in 0..<motion.amount) {
                " -> Head was at ${knotPositions[0]}, moving ${motion.direction} 1".println(debug)
                val diffAmount = when (motion.direction) {
                    Direction.UP -> Pair(0, 1)
                    Direction.DOWN -> Pair(0, -1)
                    Direction.LEFT -> Pair(-1, 0)
                    Direction.RIGHT -> Pair(1, 0)
                }

                //up/down is on the y component, left/right is on the x component
                knotPositions[0] += diffAmount
                "Head now at ${knotPositions[0]}".println(debug)

                //If the head position is ever > 1 away from the tail position, we need to move the tail
                for (k in 0..<knotPositions.lastIndex) {
                    knotPositions[k + 1] =
                        updateKnotPosition(knotPositions[k], knotPositions[k + 1])
                    "Tail[${k + 1}] at ${knotPositions[k + 1]}".println(debug)
                }

                //Adding tail position to the set, which automatically takes care of duplicates
                tailPositions.add(knotPositions.last())
            }
        }

    "Total number of tail positions: ${tailPositions.size}".println()
}

//Returns new tail position
internal fun updateKnotPosition(
    head: Pair<Int, Int>,
    tail: Pair<Int, Int>,
) = if (head.distanceBetween(tail) >= 2) { // need to update if the tail lags too far behind head
    val vertDiff = if (head.second - tail.second > 0) -1 else 1
    val horDiff = if (head.first - tail.first > 0) -1 else 1

    val diffAmount = when {
        head < tail -> Pair(horDiff, 0) // greater horizontal distance than vertical
        head > tail -> Pair(0, vertDiff) // greater vertical distance than horizontal
        else -> Pair(horDiff, vertDiff) //equal horiz/vertical distance (0,0) to (2,2) for instance
    }
    head + diffAmount
} else { // no change to tail
    tail
}

internal fun Pair<Int, Int>.distanceBetween(other: Pair<Int, Int>) =
    sqrt((other.first - first).toFloat().pow(2) + (other.second - second).toFloat().pow(2))

internal operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) =
    Pair(first + other.first, second + other.second)


internal operator fun Pair<Int, Int>.compareTo(other: Pair<Int, Int>) =
    abs(other.second - second) - abs(other.first - first)

internal data class Motion(val direction: Direction, val amount: Int) {

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    companion object {

        private const val rightDirString = "R"
        private const val leftDirString = "L"
        private const val upDirString = "U"
        private const val downDirString = "D"

        fun tryParseMotion(input: String): Motion? {
            input.split(" ", limit = 2).let {
                val direction = when (it.first()) {
                    rightDirString -> Direction.RIGHT
                    leftDirString -> Direction.LEFT
                    upDirString -> Direction.UP
                    downDirString -> Direction.DOWN
                    else -> null
                }
                val amount = it.last().toIntOrNull()

                return if (direction == null || amount == null) {
                    "Input sux".println()
                    null
                } else {
                    Motion(direction, amount)
                }
            }
        }
    }
}