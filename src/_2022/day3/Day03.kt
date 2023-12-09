package _2022.day3

import println
import readInput


const val debug = false
fun main() {

//    val priorityMap: MutableMap<Char, Int> = mutableMapOf<Char, Int>().apply {
//        (('a'..'z') + ('A'..'Z')).forEachIndexed { i, c ->
//            put(c, i + 1) // maps a..z to 1..26 and 'A'..'Z' to 27..52
//        }

    val priorityMap = mutableMapOf(
        * // spread operator, turns an Array<Pair<K,V>>> into a varargs
        ((('a'..'z') + ('A'..'Z')) zip (1..52)) //'a-z''A-z' zipped to 1-52 creates List<Pair<Char, Int>>>
            .toTypedArray() // turn into an Array
    )


    // take each line, split in half, compare each letter to see which one is doubled
    val tempSet: MutableSet<Char> = mutableSetOf()
    val sharedElf1And2Set = mutableSetOf<Char>()
    val sharedBetweenAll3List = mutableListOf<Char>()

    val lines = readInput("day3/input")
    assert(lines.size % 3 == 0) // better be groups of 3 or we're FUCKED

    //For part 2, we need to create 3 groups -- which corresponds to 3 lines at a time.
    //Do the same as part 1 with the first two elfs, find a list of duplicates between those two
    //and then for the third elf, just check for
    (0..<(lines.size / 3)).forEach outer@{ i ->
        val elf1 = lines[i*3].toSet()
        val elf2 = lines[i*3 + 1].toSet()
        val elf3 = lines[i*3 + 2].toSet()

        "\n$elf1\n$elf2\n$elf3".println(debug)

        tempSet.clear()
        sharedElf1And2Set.clear()

        //Add everything from the first elf, no dups
        tempSet.addAll(elf1)

        //by converting the second compartment to a set, we've already eliminated the duplicates, so
        //if adding to the overall set creates a dup, thats the one
        elf2.forEach {
            if (!tempSet.add(it)) {
                "Between elf 1 and 2 Duplicate found: $it".println(debug)
                sharedElf1And2Set.add(it)
            }
        }

        //Now convert the sharedItemList to a set, so we can try and add the third elfs bs
        elf3.forEach {
            if(!sharedElf1And2Set.add(it)){
                //We tried to add something from the 3rd elf, and it was a dup with the set of 1 and 2, so this is the guy!!
                "Duplicate found in the third elf $it".println(debug)
                sharedBetweenAll3List.add(it)
                return@outer // should only be one dup between all 3
            }
        }
    }

    //Fold the list of sharedItems into a total value
    var tempTotal = 0
    sharedBetweenAll3List.sumOf { c ->
        priorityMap[c]!!.also {
            tempTotal += it
            "$c is priority $it, total is now: $tempTotal".println(debug)
        }
    }.println()
}