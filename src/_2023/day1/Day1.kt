package _2023.day1

import println
import readInput

const val debug = false
val collection = CollectionCharNodes.fromStrings(
    listOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine"
    ), false
)

val stringNumMap = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

fun main() {
    val regex = Regex("\\d")
    val filePath = "_2023/day1/"

    val part1TestFile = filePath + "test_input"
    val part2TestFile = filePath + "test_input_part2"
    val realFile = filePath + "input"

    val input1 = readInput(part1TestFile)
    val input2 = readInput(part2TestFile)
    val input = readInput(realFile)


    partOne(input, regex).println()
    partTwo(input, regex).println()
}

fun partOne(input: List<String>, matchDigit: Regex) =
    input.sumOf {
        matchDigit.findFirstAndLast(it)?.sum() ?: 0
    }

fun partTwo(input: List<String>, matchDigit: Regex) = input.sumOf {
    val words = matchDigit.split(it)
    val numbers = matchDigit.findAll(it).map { it.value }.toList()

    val first = findFirstNumberAsString(it.first(), words.first(), numbers.first())
    val last = findLastNumberAsString(it.last(), words.last(), numbers.last())

    listOf(first, last).sum()
}

fun findFirstNumberAsString(
    firstChar: Char,
    word: String,
    number: String
) = if (firstChar.isLetter()) { //first char is a letter, check words first
    findNumberAsString(word)?.parseNumber(stringNumMap)
        ?: number
} else { //
    number
}

fun findLastNumberAsString(
    lastChar: Char,
    word: String,
    number: String
) = if (lastChar.isLetter()) { //first char is a letter, check words first
        //if words.first() is not actually a number, then its the next number
        findLastNumberAsString(word)?.parseNumber(stringNumMap)
            ?: number
    } else {
        number
    }


fun findNumberAsString(string: String): String? =
    collection.containsSequence(string)?.toString() ?: string.takeIf { it.isNotEmpty() }?.let {
        findNumberAsString(
            it.substring(1)
        )
    }

fun findLastNumberAsString(fullString: String): String? {
    for (i in 3..fullString.length) {
        val node = collection.containsSequence(
            fullString.subSequence(
                fullString.length - i,
                fullString.length
            )
        )?.toString()
        if (node != null) {
            return node.toString()
        }
    }

    return null
}

fun String.parseNumber(stringNumMap: Map<String, String>) = stringNumMap[this]

fun Regex.findFirstAndLast(input: String) =
    findAll(input).let { results -> results.firstOrNull() + results.lastOrNull() }

inline fun <reified T> List<T>.sum() = fold("") { acc: String, result: T ->
    when (T::class) {
        MatchResult::class -> (result as MatchResult).value
        String::class -> result
        Char::class -> result
        else -> ""
    }.let { r ->
        acc + r
    }
}.toInt()

operator fun MatchResult?.plus(that: MatchResult?): List<MatchResult>? {
    this ?: return null
    that ?: return null

    return listOf(this, that)
}