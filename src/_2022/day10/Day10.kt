package _2022.day10

import readInput

const val fileName = "input"
const val filePath = "day10/inputs/"

fun main() {
    var registerValue = 1 //now contains the location of the center of the sprite
    var onCycleNum = 0

    val operationQueue: ArrayDeque<Operation.Addx> = ArrayDeque(1)

    val horizontalPixelSize = 40
    val currentRowPixels = mutableListOf<Pixel>()
    val pixelList = mutableListOf<List<Pixel>>()

    val lines = readInput("$filePath$fileName")
        .mapNotNull(Operation::fromString)

    var i = 0
    while (i <= lines.lastIndex) {
        //Always increment the cycles, because this is going to be separate from the index counter
        onCycleNum++

        //Figure out what pixel is drawn at the current pixel position
        val pixelPosition = (onCycleNum - 1) % horizontalPixelSize
        getPixelDrawn(pixelPosition, registerValue).let {
            currentRowPixels.add(it)
        }

        //Determine when to end the row of pixels and start a new one
        if (onCycleNum % horizontalPixelSize == 0) {
            pixelList.add(currentRowPixels.toList())
            currentRowPixels.clear()
        }

        //See if there is a queued up add (aka the 2nd cycle of this addx)
        if (!operationQueue.isEmpty()) {
            operationQueue.removeFirst().let {
                registerValue += it.value
            }
        } else {
            when (val operation = lines[i]) {
                is Operation.Noop -> {
                    //do nothing
                }

                is Operation.Addx -> {
                    //For the first cycle of this add, don't do anything
                    //(except add to the queue for the next cycle)
                    operationQueue.addFirst(operation)
                }
            }

            //Only if an operation was performed do we increment the index. If popping from the
            //queue, we keep index the same to not miss an operation
            i++
        }
    }

    pixelList.pixelPrint()
}