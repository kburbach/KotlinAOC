package day5

import println
import readInput

const val debug = false

/*
            [M] [S] [S]
        [M] [N] [L] [T] [Q]
[G]     [P] [C] [F] [G] [T]
[B]     [J] [D] [P] [V] [F] [F]
[D]     [D] [G] [C] [Z] [H] [B] [G]
[C] [G] [Q] [L] [N] [D] [M] [D] [Q]
[P] [V] [S] [S] [B] [B] [Z] [M] [C]
[R] [H] [N] [P] [J] [Q] [B] [C] [F]
 1   2   3   4   5   6   7   8   9
 */
fun main() {
    // delimiter of " ", will this work to figure out what goes where?

    val stack0: ArrayDeque<Char> = ArrayDeque(listOf('G', 'B', 'D', 'C', 'P', 'R').reversed())
    val stack1: ArrayDeque<Char> = ArrayDeque(listOf('G', 'V', 'H').reversed())
    val stack2: ArrayDeque<Char> = ArrayDeque(listOf('M', 'P', 'J', 'D', 'Q', 'S', 'N').reversed())
    val stack3: ArrayDeque<Char> =
        ArrayDeque(listOf('M', 'N', 'C', 'D', 'G', 'L', 'S', 'P').reversed())
    val stack4: ArrayDeque<Char> =
        ArrayDeque(listOf('S', 'L', 'F', 'P', 'C', 'N', 'B', 'J').reversed())
    val stack5: ArrayDeque<Char> =
        ArrayDeque(listOf('S', 'T', 'G', 'V', 'Z', 'D', 'B', 'Q').reversed())
    val stack6: ArrayDeque<Char> = ArrayDeque(listOf('Q', 'T', 'F', 'H', 'M', 'Z', 'B').reversed())
    val stack7: ArrayDeque<Char> = ArrayDeque(listOf('F', 'B', 'D', 'M', 'C').reversed())
    val stack8: ArrayDeque<Char> = ArrayDeque(listOf('G', 'Q', 'C', 'F').reversed())

    val container: Array<ArrayDeque<Char>> = arrayOf(
        stack0,
        stack1,
        stack2,
        stack3,
        stack4,
        stack5,
        stack6,
        stack7,
        stack8,
    )

    if (debug) container.forEachIndexed { i, a ->
        "${i + 1}: $a".println(debug)
    }

    readInput("day5/input")
//        .take(4)
        .map {
            it.split(" ").let(Instruction::parseInstruction)
        }.let { instructions ->
            instructions.forEach {
                for (i in it.count.downTo(1)) {
                    container[it.fromIndex - 1].let { queue ->
                        queue.removeAt(queue.lastIndex + 1 - i).also { removedChar ->
                        "Moving $removedChar from ${it.fromIndex} to ${it.toIndex}".println(debug)
                        container[it.toIndex - 1].addLast(removedChar)
                    }
                    }
                }

                if (debug) container.forEachIndexed { i, a ->
                    "${i + 1}: $a".println(debug)
                }
            }
        }

    container.fold("") { acc: String, chars: ArrayDeque<Char> ->
        acc + chars.last()
    }.let {
        it.println()
    }

}

data class Instruction(val count: Int, val fromIndex: Int, val toIndex: Int) {

    companion object {
        fun parseInstruction(list: List<String>): Instruction {
            assert(list.size == 6)

            return Instruction(
                count = list[1].toInt(),
                fromIndex = list[3].toInt(),
                toIndex = list[5].toInt()
            )
        }
    }
}