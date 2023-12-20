package _2023.day3

import println
import readInput


fun main() {

    val filePath = "_2023/day3/input/"
//    val fileName = "test_input2"
    val fileName = "input"

    var previousSymbols: List<Symbol> = emptyList()
    var previousPartNumbers: List<PartNumber> = emptyList()

    readInput(filePath + fileName)
        .flatMap { currentLine ->

            val currentSymbols = currentLine.findSymbols()
            val currentPartNumbers = currentLine.findPartNumbers()

            (
                    //Current symbols that touch either previous partNumbers or current partNumbers
                    currentSymbols.flatMap { symbol ->
                        (previousPartNumbers + currentPartNumbers).filter { pN ->
                            pN.touchesIndex(symbol.index)
                        }.map {
                            symbol to it
                        }
                    } +

                    //Previous symbols that touch current partNumbers
                    previousSymbols.flatMap { symbol ->
                        currentPartNumbers.filter { pN ->
                            pN.touchesIndex(symbol.index)
                        }.map {
                            symbol to it
                        }
                    }
            ).also {
                previousPartNumbers = currentPartNumbers.toList()
                previousSymbols = currentSymbols.toList()
            }
        }
        .groupBy( //map list of Pair<Symbol, PartNumber> to a Map<Symbol, List<PartNumber>>
            keySelector = {
                it.first
            },
            valueTransform = {
                it.second
            }
        ).filterValues {//gears are symbols that touch only 2 partNumbers
            it.size == 2
        }.mapValues {//map each gear to the actual gear ratio
            it.value.fold(1) { acc, partNumber ->
                acc * partNumber.number
            }
        }
        .values
        .sum()//sum up all the gear ratios
        .println()
}

fun String.findSymbols(): List<Symbol> = this.toCharArray().withIndex().filter {
    !it.value.isLetterOrDigit() && it.value != '.'
}.map { Symbol(it.value, it.index) }

fun String.findPartNumbers(): List<PartNumber> {
    var startIndex = 0
    var value = 0
    val list = mutableListOf<PartNumber>()
    val digitsOrNull = this.toCharArray().map {
        it.digitToIntOrNull()
    }

    digitsOrNull.forEachIndexed { idx, int ->
        if (int == null) {
            if (startIndex < idx) {
                list.add(
                    PartNumber(number = value, indexRange = startIndex..<idx)
                )
                value = 0
            }
            startIndex = idx + 1
        } else {
            value = value * 10 + int
        }
    }

    if (startIndex <= digitsOrNull.lastIndex) {
        list.add(
            PartNumber(number = value, indexRange = startIndex..digitsOrNull.lastIndex)
        )
    }

    return list
}

class PartNumber(val number: Int, val indexRange: IntRange) {
    fun touchesIndex(index: Int) =
        indexRange.contains(index) || indexRange.first == index + 1 || indexRange.last == index - 1
}

class Symbol(val value: Char, val index: Int)