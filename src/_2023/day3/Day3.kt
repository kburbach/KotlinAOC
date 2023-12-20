package _2023.day3

import println
import readInput


fun main() {

    val filePath = "_2023/day3/"
    val fileName = "test_input"
//    val fileName = "input"

    val lines = readInput(filePath + fileName)

    var previousLine = ""
    var previousSymbolLocations: List<IndexedValue<Char>> = emptyList()
    var previousPartNumbers: List<PartNumber> = emptyList()

    val temp = listOf(
        "958...........319....@.....+.334..........640.................................798.........611......678.....*..630.......389............426..",
        ".........../............961..*..................608..472...........45...........*...................*....390.............*..................",
        ".....597..191......&861.......279.689.............=....*.972*..........560......900.............548..97.......240...51....2....411.......233"
    )
    lines.sumOf { currentLine ->
        previousLine.println()
        currentLine.println()
        //look for symbols in currentLine
        val currentSymbolLocations = currentLine.findSymbols()
        val currentPartNumbers = currentLine.findPartNumbers()

        (
                //Current symbols that touch either previous partNumbers or current pNs
                currentSymbolLocations.flatMap { symbol ->
                    (previousPartNumbers + currentPartNumbers).filter { pN ->
                        pN.touchesIndex(symbol.index)
                    }.map {
                        symbol to it
                    }
                }

                        +

                        //Previous symbols that touch current pNs (previous -> previous already covered)
                        previousSymbolLocations.flatMap { symbol ->
                            currentPartNumbers.filter { pN ->
                                pN.touchesIndex(symbol.index)
                            }.map {
                                symbol to it
                            }
                        }
                ).also {
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
    this.toCharArray().map {
        it.digitToIntOrNull()
    }.forEachIndexed { idx, int ->
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

    return list
}

data class PartNumber(val number: Int, val indexRange: IntRange) {
    fun touchesIndex(index: Int) =
        indexRange.contains(index) || indexRange.first == index + 1 || indexRange.last == index - 1
}