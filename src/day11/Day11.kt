package day11

import println

const val debug = false
fun main() {
    val numRounds = 2000
    val monkeys = getTestMonkeys()
//    val monkeys = getMonkeys()
    for (i in 1..numRounds) {

        if(debug) {
            "Round $i Start ->".println()
            monkeys.forEachIndexed { idx, monkey ->
                "Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()
            }
            "".println()
        }

        monkeys.forEachIndexed{idx,  monkey ->
            "Monkey $idx has ${monkey.items().count()} items to look at".println(debug)
            monkey.forEach { item ->
                    "Monkey $idx looking at $item".println(debug)
                monkey.inspectNextItem(item, debug).let {
                    monkeys[it.second].receiveItem(it.first)
                }
            }

            if(debug) {
                "".println()
                monkeys.forEachIndexed { idx, monkey ->
                    " -> Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()

                }
                "".println()
            }
        }

        if(debug) {
            "Round $i End ->".println()
            monkeys.forEachIndexed { idx, monkey ->
                "Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()
            }
            "".println()
        }



        if(debug || i == 20 || i%1000==0) {
            "After Round $i ->".println()
            monkeys.forEachIndexed { idx, m ->
                "Monkey $idx has inspected ${m.numInspectionsPerformed}".println()
            }
            println()
        }



    }

    calculateMonkeyBusiness(monkeys).println()
}

fun calculateMonkeyBusiness(monkeys: List<Monkey>) =
    monkeys.map(Monkey::numInspectionsPerformed)
        .sortedDescending()
        .take(2)
        .fold(1) { acc, next ->
            acc * next
        }
