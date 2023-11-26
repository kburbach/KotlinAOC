package day2

import readInput

val rock = Shape.Rock()
val scissors = Shape.Scissors()
val paper = Shape.Paper()

val resultsMap: Map<String, Result> = Result.values().associateBy { it.key }

val opponentMap = mapOf(
    "A" to rock,
    "B" to paper,
    "C" to scissors
)

val myMap = mapOf(
    "X" to rock,
    "Y" to paper,
    "Z" to scissors
)

const val debug = false

fun main() {
    val lines = readInput("day2/input")
//    part1(lines).let { it.println() }

    //for part2, we need to grab the 2nd element, and based on the result and the opponents throw, determine what our thrown should be
    part2(lines).let(::println)

}

fun part1(lines: List<String>): Int {
    var totalPoints = 0
    lines.forEach { round ->
        round.split(" ", limit = 2).let { // kotlin fucking sucks, why cant delimiters be named??
            val opponentShape = opponentMap[it[0]]
            val myShape = myMap[it[1]]
            getResult(
                opponent = opponentShape!!,
                me = myShape!!
            ).let { result ->
                totalPoints += getPoints(result, myShape).also { earnedPoints ->
                    if (debug) println(
                        "[OPP] $opponentShape vs [ME] $myShape.. result is $result. Gained $earnedPoints, " +
                                "${result.points} for the $result and ${myShape.points} for throwing $myShape. " +
                                "New total is ${totalPoints + earnedPoints}" //hasn't added them together in this print statement
                    )
                }
            }
        }

    }
    return totalPoints
}

fun part2(lines: List<String>): Int {
    var totalPoints = 0
    lines.forEach {round ->
        round.split(" ", limit = 2).let { // kotlin fucking sucks, why cant delimiters be named??

            val opponentShape: Shape = opponentMap[it[0]]!!
            val result = resultsMap[it[1]]!!

            getThrowBasedOnResult(opponentShape, result).let { myShape ->
                totalPoints += getPoints(result, myShape).also { earnedPoints ->
                    if (debug) println(
                        "[OPP] throws $opponentShape and I need to $result, so throw $myShape. Gained $earnedPoints, " +
                                "${result.points} for the $result and ${myShape.points} for throwing $myShape. " +
                                "New total is ${totalPoints + earnedPoints}" //hasn't added them together in this print statement
                    )
                }
            }
        }
    }

    return totalPoints
}


fun getThrowBasedOnResult(opponent: Shape, result: Result) = when (result) {
    //if its a draw, return the same shape
    Result.DRAW -> opponent

    //If i'm supposed to win, return what the opp shape loses to
    Result.WIN -> opponent.losesTo()

    //Conversely, if I'm supposed to lose, return what the opp shape beats
    Result.LOSS -> opponent.beats()
}

fun getResult(opponent: Shape, me: Shape) = when {
    opponent > me -> Result.LOSS
    opponent == me -> Result.DRAW
    else -> Result.WIN
}

fun getPoints(result: Result, me: Shape) = result.points + me.points


sealed class Shape(val points: Int) : Comparable<Shape> {
    override fun toString(): String {
        return this::class.simpleName!!
    }

    abstract fun losesTo(): Shape
    abstract fun beats(): Shape

    class Rock : Shape(1) {
        override fun compareTo(other: Shape) = when (other) {
            is Paper -> -1
            is Scissors -> 1
            is Rock -> 0
        }

        override fun losesTo() = paper
        override fun beats() = scissors
    }

    class Paper : Shape(2) {
        override fun compareTo(other: Shape) = when (other) {
            is Paper -> 0
            is Scissors -> -1
            is Rock -> 1
        }

        override fun losesTo() = scissors
        override fun beats() = rock
    }

    class Scissors : Shape(3) {
        override fun compareTo(other: Shape) = when (other) {
            is Paper -> 1
            is Scissors -> 0
            is Rock -> -1
        }

        override fun losesTo() = rock
        override fun beats() = paper
    }
}

enum class Result(val points: Int, val key: String) {
    WIN(6, "Z"),
    LOSS(0, "X"),
    DRAW(3, "Y")
}