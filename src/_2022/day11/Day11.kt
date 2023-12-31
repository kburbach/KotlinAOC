import _2022.day11.getMonkeys

/*
 * Copyright (c) 2022 by Todd Ginsberg
 */

/**
 * Advent of Code 2022, Day 11 - Monkey in the Middle
 * Problem Description: http://adventofcode.com/2022/day/11
 * Blog Post/Commentary: https://todd.ginsberg.com/post/advent-of-code/2022/_2022.day11/
 */


fun main() {
    val monkeys = getMonkeys()
    fun rounds(numRounds: Int, changeToWorryLevel: (Long) -> Long) {
        repeat(numRounds) {
            monkeys.forEach { it.inspectItems(monkeys, changeToWorryLevel) }
        }
    }
    fun solvePart1(): Long {
        rounds(20) { it / 3 }
        return monkeys.business()
    }

    fun solvePart2(): Long {
        val testProduct: Long = monkeys.map { it.test }.reduce(Long::times)
        rounds(10000) { it % testProduct }
        return monkeys.business()
    }

    solvePart2().println()

}

fun List<Monkey>.business(): Long =
    sortedByDescending { it.interactions }.let { it[0].interactions * it[1].interactions }

class Monkey(
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: Long,
    val trueMonkey: Int,
    val falseMonkey: Int
) {

    var interactions: Long = 0

    fun inspectItems(monkeys: List<Monkey>, changeToWorryLevel: (Long) -> Long) {
        items.forEach { item ->
            val worry = changeToWorryLevel(operation(item))
            val target = if (worry % test == 0L) trueMonkey else falseMonkey
            monkeys[target].items.add(worry)
        }
        interactions += items.size
        items.clear()
    }
}
