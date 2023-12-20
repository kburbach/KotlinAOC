package _2023.day3

import println
import readInput


fun main() {

    val filePath = "_2023/day3/input/"
//    val fileName = "test_input"
    val fileName = "input"

    val lines = readInput(filePath + fileName)

    var previousLine = ""
    var previousSymbolLocations: List<IndexedValue<Char>> = emptyList()
    var previousPartNumbers: List<PartNumber> = emptyList()

    lines.sumOf { currentLine ->
        previousLine.println()
        currentLine.println()

        val currentSymbolLocations = currentLine.findSymbols()
        val currentPartNumbers = currentLine.findPartNumbers()

        //Current symbols that touch either previous partNumbers or current pNs
        val currentSymbolsTouchedPartNumbers = currentSymbolLocations.flatMap { symbol ->
            (previousPartNumbers + currentPartNumbers).filter { pN ->
                pN.touchesIndex(symbol.index)
            }.map {
                symbol to it
            }
        }

        //Previous symbols that touch current pNs (previous -> previous already covered)
        val previousSymbolsTouchedPartNumbers = previousSymbolLocations.flatMap { symbol ->
            currentPartNumbers.filter { pN ->
                pN.touchesIndex(symbol.index)
            }.map {
                symbol to it
            }
        }
        
        (currentSymbolsTouchedPartNumbers + previousSymbolsTouchedPartNumbers)
            .also {
                " -> ${it.size} total touches".println()
            }
            .sortedBy {
                it.first.index
            }
            .sumOf {
                val partNumber = it.second
                val symbol = it.first

                "   -> ${symbol.value}(${symbol.index}) touches ${partNumber.number}".println()

                partNumber.number
            }.also {
                //USE TOLIST() to actually make a copy
                previousPartNumbers = currentPartNumbers.toList()
                previousSymbolLocations = currentSymbolLocations.toList()
                previousLine = currentLine
                "\n -> This Row Total: $it\n".println()
            }
    }.println()

}

fun String.findSymbols(): List<IndexedValue<Char>> = this.toCharArray().withIndex().filter {
    !it.value.isLetterOrDigit() && it.value != '.'
}

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

data class PartNumber(val number: Int, val indexRange: IntRange) {
    fun touchesIndex(index: Int) =
        indexRange.contains(index) || indexRange.first == index + 1 || indexRange.last == index - 1
}