package day11

import println

fun main() {
    val numRounds = 20
//    val monkeys = getTestMonkeys()
    val monkeys = getMonkeys()
    for (i in 1..numRounds) {

        "Round $i Start ->".println()
        monkeys.forEachIndexed { idx, monkey ->
            "Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()
        }
        "".println()
        monkeys.forEachIndexed{idx,  monkey ->
            "Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()
            monkey.forEach { item ->
                monkey.inspectNextItem(item).let {
                    monkeys[it.second].receiveItem(it.first)
                }
            }
            "".println()
            monkeys.forEachIndexed { idx, monkey ->
                " -> Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()

            }
            "".println()
        }

        "Round $i End ->".println()
        monkeys.forEachIndexed { idx, monkey ->
            "Monkey $idx has ${monkey.items()} -> ${monkey.items().count()}".println()
        }
        "".println()



            monkeys.forEachIndexed { idx, m ->
                "Monkey $idx has inspected ${m.numInspectionsPerformed}".println()
            }
            println()



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
