package _2022.day6

import println
import readInput

const val debug = true
fun main() {

    val secretMessage = readInput("day6/input")[0]
    val charMessageArray = secretMessage.toCharArray()//.take(10)
    val secretQueue = ArrayDeque<Char>()
    val secretSet = mutableSetOf<Char>()

    charMessageArray.forEachIndexed { i, c ->
        "Adding $c to queue/set".println(debug)
        secretQueue.add(c)
        secretQueue.println(debug)

        if (!secretSet.add(c)) {
            "$c is already in the set".println(debug)
            //this character is already in the set, clear the set, remove up to this character in the dequeue, add back to set, keep trucking
            secretSet.clear()

            do {
                secretQueue.println()
                "removing ${secretQueue.first()}".println(debug)
            } while (secretQueue.removeFirst() != c)

            secretSet.addAll(secretQueue)
            "Set now contains $secretSet".println(debug)
            "Queue now contains $secretQueue".println(debug)

        } else if(secretSet.size == 14){
            "Secret Code found: $secretQueue".println(debug)
            "${i+1}".println(debug)
            return
        }
    }


}