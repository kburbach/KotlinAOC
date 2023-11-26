package day9

import println
import readInput
import day9.Motion.Direction
import print
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

const val debug = true
fun main() {

    val tailPositions =
        mutableSetOf<Pair<Int, Int>>() //set to keep track of [x,y] positions that the tail has visited

    //each knot starts at (0,0)
    val knotPositions = MutableList(7) {
        0 to 0
    }

    readInput("day9/test").also(::println).mapNotNull(Motion::tryParseMotion)
        .forEach { motion ->
            //Gotta do this for each step
            "\nMoving ${motion.direction} ${motion.amount}".println(debug)

            for (i in 0..<motion.amount) {

                //Left and down are moving to the negative, up and to the right are to the positive
                val moveHead: Int.(Int) -> Int = when (motion.direction) {
                    Direction.UP -> Int::plus
                    Direction.DOWN -> Int::minus
                    Direction.LEFT -> Int::minus
                    Direction.RIGHT -> Int::plus
                }

                //up/down is on the y component, left/right is on the x component
                "-> Head was at ${knotPositions.first()}, but moving ${motion.direction} by 1 ->".print(
                    debug
                )
                knotPositions[0] = when (motion.direction) {
                    Direction.UP, Direction.DOWN -> knotPositions[0].copy(
                        second = knotPositions[0].second.moveHead(
                            1
                        )
                    )

                    Direction.RIGHT, Direction.LEFT -> knotPositions[0].copy(
                        first = knotPositions[0].first.moveHead(
                            1
                        )
                    )
                }

                //Assign the new position back to the array of positions
                " Now at ${knotPositions[0]}".println(debug)

                //If the head position is ever > 1 away from the tail position, we need to move the tail
                for (k in 0..<knotPositions.lastIndex) {
                    knotPositions[k + 1] =
                        updateKnotPosition(knotPositions[k], knotPositions[k + 1], k+1)
                }

                //Adding tail position to the set
                tailPositions.add(knotPositions.last()).also {
                    if (it) {
                        "Added ${knotPositions.last()} to set".println(debug)
                    } else {
                        "Didn't add ${knotPositions.last()}, was a dup".println(debug)
                    }
                }
            }
        }

    "Total number of tail positions: ${tailPositions.size}".println()
}

//Returns new tail position
internal fun updateKnotPosition(
    headPosition: Pair<Int, Int>,
    tailPosition: Pair<Int, Int>,
    tailIndex: Int //for debug
): Pair<Int, Int> {

    var newTailPosition = tailPosition
    if (headPosition.distanceBetween(tailPosition) >= 2) {// gotta move!
        "-> Moving Tail${tailIndex}!".println(debug)
        val headIsRightOfTail = headPosition.first - tailPosition.first > 0
        val headIsUpOfTail = headPosition.second - tailPosition.second > 0

        if (headPosition.first - tailPosition.first == 0) {// same column or x coordinate
           // "    -> H and T share X coordinate".println(debug)
            newTailPosition =
                if (headIsUpOfTail) { // head is further up, move tail to head.y -1
                    tailPosition.copy(second = headPosition.second - 1)
                } else { // head is further left, move tail to head.y + 1
                    tailPosition.copy(second = headPosition.second + 1)
                }
        } else if (headPosition.second - tailPosition.second == 0) { //same row or y coordinate
          //  "    -> H and T share Y coordinate".println(debug)
            newTailPosition =
                if (headIsRightOfTail) { // head is further up, move tail to head.second -1
                    tailPosition.copy(first = headPosition.first - 1)
                } else { // head is further left, move tail to head.second + 1
                    tailPosition.copy(first = headPosition.first + 1)
                }
        } else { // This is the hard one
          //  "    -> H and T are diagonally!".println(debug)
            val furtherAwayVertically =
                headPosition.isFurtherAwayVertically(tailPosition)

            if (headIsUpOfTail) { //head is above of tail
              //  "       -> H is above T ".print(debug)
                newTailPosition =
                    if (furtherAwayVertically) { // if its further away vertically,
                        // the tail is going to match the head's vertical component
                        //it doesn't matter who is left of who
                 //       "and further away vertically".println(debug)
                        Pair(
                            first = headPosition.first,
                            second = headPosition.second - 1
                        )
                    }
                    //if its further away horizontally, the tail is going to match the head's
                    // vertical component, BUT it does matter if the head.x > tail.x or not
                    else if (headIsRightOfTail) { // if head.x > tail.x, move tail.x to head.x -1
                        //"and further away horizontally".println(debug)
                      //  "         -> H is right of T ".println(debug)
                        Pair(
                            first = headPosition.first - 1,
                            second = headPosition.second
                        )
                    } else { //head.x < tail.x, but !furtherAwayVertically
                      //  "and further away horizontally".println(debug)
                       // "         -> H is left of T ".println(debug)
                        Pair(
                            first = headPosition.first + 1,
                            second = headPosition.second
                        )
                    }

            } else { //head below tail
               // "       -> H is below T ".print(debug)
                newTailPosition =
                    if (furtherAwayVertically) { // if its further away vertically, the tail is going to match the head's vertical component
                      //  "and further away vertically".println(debug)
                        Pair(
                            first = headPosition.first,
                            second = headPosition.second + 1
                        )
                    } else if (headIsRightOfTail) { // if head.x > tail.x, move tail.x to head.x -1
                       // "and further away horizontally".println(debug)
                       // "         -> H is right of T ".println(debug)
                        Pair(
                            first = headPosition.first - 1,
                            second = headPosition.second
                        )
                    } else { //if its further away horizontally, the tail is going to match the head's vertical component. b
                       // "and further away horizontally".println(debug)
                      //  "         -> H is left of T ".println(debug)
                        Pair(
                            first = headPosition.first + 1,
                            second = headPosition.second
                        )
                    }
            }
        }
    }
    return newTailPosition.also {
        "Tail${tailIndex} at $it".println(debug)
    }
}

internal fun Pair<Int, Int>.distanceBetween(other: Pair<Int, Int>) =
    sqrt((other.first - first).toFloat().pow(2) + (other.second - second).toFloat().pow(2))

//If the vertical difference between 2 points is greater than the horizontal difference between 2 points
internal fun Pair<Int, Int>.isFurtherAwayVertically(other: Pair<Int, Int>) =
    abs(other.second - second) > abs(other.first - first)

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