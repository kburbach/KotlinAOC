package day4

import println
import readInput
const val debug = false
fun main() {
    //pull out the 2 elf's ranges, convert to sets, find intersection/union (cant remember which is which)
    val lines = readInput("day4/input").also { it.size.println() }
    var count = 0

    lines.forEach { groups ->
        groups.split(",", limit = 2).let { group ->
            group[0].split("-", limit = 2).let { elf0 ->
                group[1].split("-", limit = 2).let { elf1 ->

                    if (isPartiallyOverlapping(
                            s0 = elf0[0].toInt(),
                            e0 = elf0[1].toInt(),
                            s1 = elf1[0].toInt(),
                            e1 = elf1[1].toInt()
                        )
                    ){
                        "$elf0 overlaps $elf1, or maybe $elf1 overlaps $elf0".println(debug)
                        count++
                    } else {
                        "$elf0 and $elf1 DONT OVERLAP".println(debug)
                    }
                }
            }
        }
    }

    count.println()
}

fun isOverlapping(start0: Int, end0: Int, start1: Int, end1: Int) =
    (start0 <= start1 && end0 >= end1) || (start1 <= start0 && end1 >= end0)

fun isPartiallyOverlapping(s0: Int, e0: Int, s1: Int, e1: Int) =
    (s0 in s1..e1) || (e0 in s1..e1) || (s1 in s0..e0) || (e1 in s0..e0)
