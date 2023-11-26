package day7

import day7.Node.*
import day7.Command.DestinationType
import println
import readInput

sealed class Node(open val name: String, open var parent: Dir? = null) {
    abstract val size: Int

    data class File(
        override val name: String,
        override val size: Int,
        override var parent: Dir? = null
    ) : Node(name, parent) {
        override fun toString() = "File[$name, parent:[${parent?.name}]"
    }

    data class Dir(
        override val name: String,
        val children: MutableList<Node> = mutableListOf(),
        override var parent: Dir? = null
    ) : Node(name, parent) {
        override fun toString() =
            "Dir[$name, parent:[${parent?.name}], children:[${children.size}]]"

        override val size by lazy {
            children.sumOf(Node::size)
        }

        fun addChild(child: Node) {
            child.parent = this
            children.add(child)
        }

        companion object {
            const val dirString = "dir"
        }
    }

    companion object {

        //take an input line and turns it into a File node or a Dir node
        fun parseNode(input: String): Node = input.split(" ").let { inputParts ->
            if (inputParts.first() == Dir.dirString) { // this is a directory
                return Dir(inputParts.last())
            } else { // this is listing a file with [size, name]
                return File(
                    name = inputParts.last(),
                    size = inputParts.first().toInt()
                )
            }
        }
    }
}

internal data class Command(val destinationType: DestinationType, val changeTo: String? = null) {

    internal enum class DestinationType {
        ROOT, // "/'"
        PARENT, // ".."
        CHILD // "xxx"
    }

    override fun toString(): String {
        return "cd to $changeTo which is type $destinationType"
    }

    companion object {
        private const val rootDirString = "/"
        private const val parentDirString = ".."

        fun tryConvertToCommand(input: String): Command? {
            return input.split(" ", limit = 3).let {
                it.takeIf {//["$", "cd", "XXX"]
                    it.size == 3
                }?.let { parts ->
                    Command(
                        destinationType = convertToDestinationType(parts[2]),
                        changeTo = parts[2]
                    )
                }
            }
        }

        private fun convertToDestinationType(dest: String) = when (dest) {
            rootDirString -> DestinationType.ROOT
            parentDirString -> DestinationType.PARENT
            else -> DestinationType.CHILD
        }
    }
}

fun findDirectoriesSizedLessThan(size: Int, currentCount: Int, parent: Dir): Int {
    "Current count is $currentCount".println(debug)
    var temp = 0
    parent.children.filterIsInstance<Dir>().forEach { child ->
        if (child.size <= size) {
            temp += child.size
            "Child ->$child has a size of ${child.size}".println(debug)
            "!!!!! temp is now $temp".println(debug)
        } else {
            "Child ->$child has a size of ${child.size}".println(debug)
        }
        temp += findDirectoriesSizedLessThan(size, temp + currentCount, child)
    }

    return temp
}

var i = 0
fun findDirectoryClosestInSizeTo(
    size: Int,
    parent: Dir,
    currentClosestSize: Int = Int.MAX_VALUE
): Int {
    i++

    //initially, set the closet size to max value
    var tempClosestSize = currentClosestSize

    //for each child, if the dir size is > the size needed, but < the currentClosestSize it becomes
    //the currentClosestSize
    parent.children.filterIsInstance<Dir>().forEach { child ->
        child.size.let { childSize ->
            if(childSize < size) return@forEach // if this child has too small of a size, don't bother checking any of the children
            if (childSize in size..<tempClosestSize) {
                tempClosestSize = childSize
            }
        }
        tempClosestSize = findDirectoryClosestInSizeTo(size, child, tempClosestSize)
    }
    return tempClosestSize
}

const val debug = true
fun main() {
    val rootDir = Dir("/")
    var currentNodePtr: Node =
        rootDir //use this for traversing around and adding children and all that shiiiit

    //Build the shit first, then go back and find the total?
    readInput("day7/input").forEach { input ->
        input.println(debug)
        //each output is either a command, or the results from a command (displaying the files)
        if (input.startsWith("$")) {
            Command.tryConvertToCommand(input)?.let { command ->
                "->Parsed cmd: $command".println()
                currentNodePtr = when (command.destinationType) {
                    DestinationType.ROOT -> rootDir
                    DestinationType.PARENT -> currentNodePtr.parent!!
                    DestinationType.CHILD -> {
                        //if we've got the cd child command we better be currently pointing to
                        //a directory of were FUCKED (and that child better exist
                        (currentNodePtr as Dir).children.find { child ->
                            child.name == command.changeTo
                        }!!
                    }
                }
                "->currentPtr now points to $currentNodePtr".println(debug)
            }
        } else { //input is now the result of an LS command
            with(currentNodePtr as Dir) {
                Node.parseNode(input).let { node ->
                    node.parent = this
                    addChild(node)
                    "->adding $node to $currentNodePtr".println(debug)
                }
            }
        }
    }

    "\n\n".println(debug)


    //for part 1
//    findDirectoriesSizedLessThan(100000, 0, rootDir).println(debug)

    //for part 2
    val usedSpace = rootDir.size.also {
        it.println(debug)
    } // this is 40,268,565 from previous times running and seeing output

    val totalDiskSpace = 70000000 //70,000,000
    val currentUnusedSpace = totalDiskSpace - usedSpace
    val needUnusedSpacedOf = 30000000 //30,000,000
    "Current unused space: $currentUnusedSpace, need $needUnusedSpacedOf, so...".println(debug)

    val amountOfSpaceNeeded = needUnusedSpacedOf - currentUnusedSpace
    "Need to free up $amountOfSpaceNeeded space ".println(debug)

    findDirectoryClosestInSizeTo(
        size = amountOfSpaceNeeded,
        parent = rootDir
    ).println(debug)
    "looped $i times".println(debug)
}