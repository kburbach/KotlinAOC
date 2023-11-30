package day11

import print
import println

class Monkey(
    vararg currentItems: Int,
    private val testIsDivisibleBy: Int,
    private val inspectionOperation: (Factor).() -> Factor,
    val passedTestMonkey: Int,
    val failedTestMonkey: Int
) : Iterator<Factor> {
    var numInspectionsPerformed = 0
        private set

    private val testOperation: (Int).() -> Boolean = {
        (rem(testIsDivisibleBy) == 0).also {
            "      --> which is ${if (!it) "not" else ""} divisible by $testIsDivisibleBy".println()
        }
    }

    private val currentItems = ArrayDeque(elements = currentItems.map(::Factor))

    fun inspectNextItem(factor: Factor): Pair<Factor, Int> {
        val newFactor = factor.inspectionOperation().gotBoredOperation()
        val total = newFactor.totalValue
        " --> Inspecting $factor (totalValue is ${factor.totalValue})...".print()
        "new Factor is $newFactor, (totalValue is ${newFactor.totalValue})".println()
        return newFactor.let {
            when (total.testOperation()) {
                true -> passedTestMonkey
                false -> failedTestMonkey
            }.let { monkeyIndex ->
                " --> Sending $it to monkey$monkeyIndex".println()
                it to monkeyIndex

            }.also {
                numInspectionsPerformed++
                "    --> now inspected $numInspectionsPerformed items".println()
            }
        }
    }
//    fun inspectNextItem(worryLevel: Int): Pair<Int, Int>  = 1 to 1
//        val newWorryLevel = worryLevel.inspectionOperation()
//        return when (worryLevel.testOperation()) {
//            true -> newWorryLevel to passedTestMonkey
//            false -> newWorryLevel to failedTestMonkey
//        }
//    }

    fun receiveItem(item: Factor) = currentItems.addLast(item)
    fun items() = currentItems.toList()

    companion object {
        val gotBoredOperation: (Factor).() -> Factor = {
            Factor(totalValue.floorDiv(3))
        }
        val keepWorryLevelsManageable: (Int).() -> Int = {
            this
        }
    }

    override fun hasNext() = currentItems.isNotEmpty()
    override fun next() = currentItems.removeFirst()
}