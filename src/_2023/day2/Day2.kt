package _2023.day2

import println
import readInput

const val debug = false
fun main() {
    val filePath = "_2023/day2/"
//    val fileName = "test_input"
    val fileName = "input"
    val lines = readInput(filePath + fileName)

    val redMax = 12
    val greenMax = 13
    val blueMax = 14

    val digitRegex = Regex("\\d+")
    val blueRegex = Regex("\\d+ blue")
    val redRegex = Regex("\\d+ red")
    val greenRegex = Regex("\\d+ green")

    var gameIdTotalPart1 = 0
    var gameIdTotalPart2 = 0

    val part1 = false // set to false for part2

    lines.forEach lines@{ game ->
        val id = digitRegex.find(game)!!.value.toInt()

        var minGreen = Int.MIN_VALUE
        var minRed = Int.MIN_VALUE
        var minBlue = Int.MIN_VALUE

        game.split(';').forEach { round ->
            val blueTotal = findColorsInRound(blueRegex, round)
            val redTotal = findColorsInRound(redRegex, round)
            val greenTotal = findColorsInRound(greenRegex, round)

            minBlue = blueTotal.coerceAtLeast(minBlue)
            minGreen = greenTotal.coerceAtLeast(minGreen)
            minRed = redTotal.coerceAtLeast(minRed)

            //part1 only
            if (part1 && (blueTotal > blueMax || redTotal > redMax || greenTotal > greenMax)) {
                "Game $id impossible, skipping".println(debug)
                return@lines
            }

        }
        "Game $id possible".println(debug)
        gameIdTotalPart1 += id

        "finding power: $minBlue blues, $minGreen greens, $minRed reds : ${minBlue * minGreen * minRed}".println(debug)
        gameIdTotalPart2 += (minBlue * minGreen * minRed)
    }

    if (part1) {
        gameIdTotalPart1.println()
    } else {
        gameIdTotalPart2.println()
    }
}

fun findColorsInRound(regex: Regex, round: String): Int {
    val digitRegex = Regex("\\d+")

    return regex.find(round)?.let {
        digitRegex.find(it.value)?.value?.toInt()
    } ?: 0
}