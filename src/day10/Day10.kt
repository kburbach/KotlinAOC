package day10

import print
import println
import readInput

const val debug = true
//const val fileName = "test1"
//const val fileName = "test2"
const val fileName = "input"
fun main() {
    var registerValue = 1
    var onCycleNum = 0 //0 or 1 here?

    val signalStrengths = mutableListOf<Int>()

    //Dont know how long the input is, so lazily create a sequence of 20, 60, 100, 140, etc
    val specialCycles = listOf(20, 60, 100, 140, 180, 220)
    val operationQueue: ArrayDeque<Operation.Addx> = ArrayDeque()
    val lines = readInput("day10/inputs/$fileName")
        .mapNotNull(Operation::fromString)

    var i = 0
    while (i <= lines.lastIndex) {
        //Always increment the cycles, because this is going to be separate from the index counter
        onCycleNum++
        " -> Cycle #$onCycleNum starting".println(debug)
        " -> Index is $i".println(debug)

        //Check for special cycle first I guess
        if( onCycleNum in specialCycles){
            "Cycle $onCycleNum reached! Adding ${registerValue*onCycleNum} to list".print(debug)
            signalStrengths.add(registerValue * onCycleNum)
        }

        //See if there is a queued up add (aka the 2nd cycle of this addx)
        if (!operationQueue.isEmpty()) {
            operationQueue.removeFirst().let {
                "  --> Pulling $it from queue".println(debug)
                registerValue += it.value
                "  --> registerValue is now $registerValue".println(debug)
            }
        } else {
            val operation = lines[i]
            "New operation is $operation: ".println(debug)

            when (operation) {
                is Operation.Noop -> {
                    //do nothing
                }

                //If its an ADDx operation, just add it to the queue for the next cycle, but keep the index
                is Operation.Addx -> {
                    //For the first cycle of this add,
                    // don't do anything (except add to the queue for next time
                    operationQueue.addFirst(operation)
                    " --> Added $operation to operationQueue".println(debug)
                }
            }

            //Only if an operation was preformed do we increment the index. If popping from the queue
            //we keep index the same to not miss an operation
            i++
        }



        " -> Cycle #$onCycleNum finished\n".println(debug)

    }

    signalStrengths.println(debug)
    signalStrengths.sum().println(debug)
}

sealed class Operation(numCycles: Int) {

    data class Addx(val value: Int) : Operation(2)
    data object Noop : Operation(1)

    companion object {
        fun fromString(s: String): Operation? {

            val parts = s.split(" ", limit = 2)

            val cmd = parts[0]
            val value = parts.getOrNull(1)?.toInt()

            return when (cmd) {
                "addx" -> value?.let { Addx(it) }
                "noop" -> Noop
                else -> null
            }
        }

    }
}