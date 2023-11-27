package day1

import println
import readInput

fun main() {

    val lines = readInput("input")
    var maxCalories1: Int = 0
    var maxCalories2: Int = 0
    var maxCalories3: Int = 0
    var currentCalories = 0

    lines.forEach {
        if(it.isNotBlank()){
            currentCalories += it.toInt()
        } else {
            if(currentCalories > maxCalories1) maxCalories1 = currentCalories
            if(maxCalories1 > maxCalories2) maxCalories2 = maxCalories1.also { maxCalories1 = maxCalories2 }
            if(maxCalories2 > maxCalories3) maxCalories3 = maxCalories2.also { maxCalories2 = maxCalories3 }
            currentCalories = 0
        }
    }

    maxCalories1.println()
    maxCalories2.println()
    maxCalories3.println()

    "Total ${maxCalories1 + maxCalories2 + maxCalories3}".println()
    //answer, 68802
}
