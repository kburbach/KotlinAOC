package day8

import println
import readInput

data class Tree(
    val height: Int,
    var isVisible: Boolean = false,
    val openSpace: OpenSpace = OpenSpace()
) {
    var topTree: Tree? = null
    var leftTree: Tree? = null
    var rightTree: Tree? = null
    var bottomTree: Tree? = null

    val scenicScore: () -> Int = openSpace::calculateOpenSpaceScore
    var alreadyVisited = false

    override fun toString() = "$height ($openSpace)"

    data class OpenSpace(
        var left: Int = 0,
        var top: Int = 0,
        var bottom: Int = 0,
        var right: Int = 0
    ) {
        fun calculateOpenSpaceScore() = left * top * right * bottom
        override fun toString() = "[t:$top, l:$left, b:$bottom, r:$right]"
    }
}

const val debug = false
fun main() {

    val lines = readInput("day8/input")

//    val forest: Array<Array<Tree>> = Array(lines.size) {
//        emptyArray()
//    }

    var topLeftTree = Tree(-1)
    var treePtr: Tree? = topLeftTree
    var previousRowPtr: Tree? = null
    var currentRowBeginningPointer: Tree? = null
    lines.forEach { input ->
        //convert each line to a CharArray, map to Int (Creating List<Int>),
        //turn to IntArray and spread for varargs


        input.toCharArray()
            .map(Char::digitToInt)
            .mapIndexed { idx, it ->
                Tree(it).apply {
                    "Creating Tree($this) ->".println(debug)

                    //If this is the first column, set the currentRow pointer to this and null out the tree ptr so the rows aren't linked
                    if (idx == 0) {
                        "--> Setting this as previousRowStartPtr".println(debug)

                        currentRowBeginningPointer = this
                    }

                    previousRowPtr?.bottomTree = this
                    topTree = previousRowPtr
                    "--> linked to $previousRowPtr on top".println(debug)
                    "--> linked $previousRowPtr bottom to $this".println(debug)
                    previousRowPtr = previousRowPtr?.rightTree



                    leftTree = treePtr
                    treePtr?.rightTree = this
                    "--> linked to $treePtr on left".println(debug)
                    "--> linked $treePtr right to $this".println(debug)

                    treePtr = this

                }
            }

        //Reset for new row
        previousRowPtr = currentRowBeginningPointer
        treePtr = null

        ("\n\n").println(debug)
    }

    //unlink the dummy tree node
    treePtr = topLeftTree
    topLeftTree.rightTree?.leftTree = null
    treePtr = topLeftTree.rightTree!!
    topLeftTree.rightTree = null
    topLeftTree = treePtr as Tree

    var currentColumnPtr: Tree? = treePtr
    var currentRowPtr: Tree? = treePtr

    calculateOpenSpace(topLeftTree).println()

    if (debug) {
        while (currentColumnPtr != null) {
            print("$currentColumnPtr <->")
            currentColumnPtr = currentColumnPtr.rightTree

            if (currentColumnPtr == null) {// move to the next row, it could still be null, in which case we are done
                "\n| | |".println()
                currentColumnPtr = currentRowPtr?.bottomTree
                currentRowPtr = currentRowPtr?.bottomTree
            }
        }
    }

//    (countVisibleTrees(forest2DArray)).println()
//    calculateOpenSpace(forest).println()
//
//    if (debug) forest.forEach {
//        it.toList().println()
//    }
}

fun Array<Array<Tree>>.get(j: Int, i: Int) = when {
    i < 0 || j < 0 -> null
    j > this.lastIndex -> null
    i > this.first().lastIndex -> null
    else -> this[j][i]
}

internal fun calculateOpenSpace(startingTree: Tree?): Int {

    if (startingTree == null || startingTree.alreadyVisited) return 0
    startingTree.alreadyVisited = true

    doCalculateOpenSpace(
        startingTree,
        fetchNextTree = {
            topTree
        },
        assignScenicScore = {
            top = it
        }
    )

    doCalculateOpenSpace(
        startingTree,
        fetchNextTree = {
            leftTree
        },
        assignScenicScore = {
            left = it
        }
    )

    doCalculateOpenSpace(
        startingTree,
        fetchNextTree = {
            bottomTree
        },
        assignScenicScore = {
            bottom = it
        }
    )

    doCalculateOpenSpace(
        startingTree,
        fetchNextTree = {
            rightTree
        },
        assignScenicScore = {
            right = it
        }
    )

    //Only traverse outwards from bottom and right (because we are starting in the top left corner
    "scenic score for Tree($startingTree) is ${startingTree.scenicScore()}".println(debug)
    return startingTree.scenicScore().coerceAtLeast(
        calculateOpenSpace(startingTree.bottomTree).coerceAtLeast(
            calculateOpenSpace(startingTree.rightTree)
        )
    )
}

internal fun doCalculateOpenSpace(
    currentTree: Tree,
    fetchNextTree: Tree.() -> Tree?,
    assignScenicScore: Tree.OpenSpace.(Int) -> Unit
) {
    var nextTree: Tree? = currentTree.fetchNextTree()
    "Trying to calculate open space for Tree($currentTree) -> next Tree($nextTree)".println(debug)
    if (nextTree == null) {
        " --> Next tree $nextTree was null, sending back a 0".println(debug)
        currentTree.openSpace.assignScenicScore(0)
        return
    }

    if (currentTree.height <= nextTree.height) {
        " --> Next tree $nextTree is taller, sending back a 1".println(debug)
        currentTree.openSpace.assignScenicScore(1)
        return
    }

    var visibleToRight = 0
    while (nextTree != null) {
        visibleToRight++
        if (currentTree.height > nextTree.height) {
            " --> Next tree $nextTree was shorter, incrementing and checking ${nextTree.fetchNextTree()}".println(
                debug
            )
            nextTree = nextTree.fetchNextTree()
        } else {
            break // gtfo the loop
        }

    }
    " --> Sending back a score of $visibleToRight".println(debug)
    currentTree.openSpace.assignScenicScore(visibleToRight)
    return
}

//
//fun calculateOpenSpace(
//    currentTree: Tree,
//    nextTree: Tree?,
//    forest: Array<Array<Tree>>,
//    currentIsOnEdge: Tree.OpenSpace.() -> Unit,
//    currentGreaterThanOpp: Tree.OpenSpace.(Tree.OpenSpace) -> Unit,
//    currentEqualOpp: Tree.OpenSpace.() -> Unit,
//    treeDirection: String = "" //debug
//) {
//    "\nComparing Tree (${currentTree.height}) to $treeDirection tree (${nextTree?.height})".println(
//        debug
//    )
//
//    if (nextTree == null) {
//        currentIsOnEdge(currentTree.openSpace)
//    } else if (currentTree.height > nextTree.height) {
//        currentGreaterThanOpp(
//            currentTree.openSpace, calculateOpenSpace(
//                currentTree, nextTree.next
//            )
//        )
//    } else {
//        currentEqualOpp(currentTree.openSpace)
//    }
//}
//
//
//fun updateLeft(currentTree: Tree, nextTree: Tree?) {
//
//    calculateOpenSpace(
//        currentTree = currentTree,
//        nextTree = nextTree,
//        currentIsOnEdge = {
//            left = 0
//            "->On Edge, setting left to $left".println(debug)
//        },
//        currentGreaterThanOpp = {
//            left = it.left + 1
//            "->Greater than left, updating openSpaceLeft to $left (previous was ${it.left}".println(
//                debug
//            )
//        },
//        currentEqualOpp = {
//            left = 1
//            "->Equal to left, setting openSpaceLeft to 1".println(debug)
//        },
//        "left"
//    )
//}
//
//fun updateTop(currentTree: Tree, previousTree: Tree?) {
//
//    calculateOpenSpace(
//        currentTree = currentTree,
//        nextTree = previousTree,
//        currentIsOnEdge = {
//            top = 0
//            "->On Edge, setting top to $top".println(debug)
//        },
//        currentGreaterThanOpp = {
//            top = it.top + 1
//            "->Greater than top, updating openSpaceTop to $top (previous was ${it.top}".println(
//                debug
//            )
//        },
//        currentEqualOpp = {
//            top = 1
//            "->Equal to top, setting openSpaceTop to 1".println(debug)
//        },
//        "top"
//    )
//
//}
//
//fun updateRight(backwardsTree: Tree, previousTree: Tree?) {
//
//    calculateOpenSpace(
//        currentTree = backwardsTree,
//        nextTree = previousTree,
//        currentIsOnEdge = {
//            right = 0
//            "->On Edge, setting right to $right".println(debug)
//        },
//        currentGreaterThanOpp = {
//            right = it.right + 1
//            "->Greater than right, updating openSpaceRight to $right (previous was ${it.right}".println(
//                debug
//            )
//        },
//        currentEqualOpp = {
//            right = 1
//            "->Equal to right, setting openSpaceRight to 1".println(debug)
//        },
//        "right"
//    )
//}
//
//fun updateBottom(backwardsTree: Tree, previousTree: Tree?) {
//
//    calculateOpenSpace(
//        currentTree = backwardsTree,
//        nextTree = previousTree,
//        currentIsOnEdge = {
//            bottom = 0
//            "->On Edge, setting bottom to $bottom".println(debug)
//        },
//        currentGreaterThanOpp = {
//            bottom = it.bottom + 1
//            "->Greater than bottom, updating openSpaceBottom to $bottom (previous was ${it.bottom}".println(
//                debug
//            )
//        },
//        currentEqualOpp = {
//            bottom = 1
//            "->Equal to bottom, setting openSpaceBottom to 1".println(debug)
//        })
//}

internal fun countVisibleTrees(forest: Array<Array<Tree>>): Int {
    val fromLeftMaxHeight = IntArray(forest.size) {
        Int.MIN_VALUE
    }
    val fromRightMaxHeight = IntArray(forest.size) {
        Int.MIN_VALUE
    }
    var fromTopMaxHeight: Int
    var fromBottomMaxHeight: Int

    var visibleCount = 0

    val lastJindex = forest.lastIndex
    val lastIindex = forest.first().lastIndex

    for (i in 0..lastIindex) {
        //Because we are traversing ALL of each column (j) before changing rows, we can use a single
        //variable for top/bottom heights respectively. But we need an array of max left/right heights
        fromTopMaxHeight = Int.MIN_VALUE
        fromBottomMaxHeight = Int.MIN_VALUE

        for (j in 0..lastJindex) {
            val currentTree = forest[j][i]
            val backwardsTree = forest[lastJindex - j][lastIindex - i]


            //going right aka visible from left first
            if (currentTree.height > fromLeftMaxHeight[j]) {

                //check to see if this is the new tallest from the left
                fromLeftMaxHeight[j] = fromLeftMaxHeight[j].coerceAtLeast(currentTree.height)

                //Only increase the visibleCount if this tree wasn't previously visible
                if (!currentTree.isVisible) {
                    visibleCount++
                }
                currentTree.isVisible = true
            }

            //checking visible from top
            if (currentTree.height > fromTopMaxHeight) {

                //check to see if this is the new tallest from the top
                fromTopMaxHeight = fromTopMaxHeight.coerceAtLeast(currentTree.height)

                //Only increase the visibleCount if this tree wasn't previously visible
                if (!currentTree.isVisible) {
                    visibleCount++
                }
                currentTree.isVisible = true
            }

            //checking visible from right
            if (backwardsTree.height > fromRightMaxHeight[lastJindex - j]) {

                //check to see if this is the new tallest from the right
                fromRightMaxHeight[lastJindex - j] =
                    fromRightMaxHeight[lastJindex - j].coerceAtLeast(backwardsTree.height)

                //Only increase the visibleCount if this tree wasn't previously visible
                if (!backwardsTree.isVisible) {
                    visibleCount++
                }
                backwardsTree.isVisible = true
            }

            //checking visible from the bottom
            if (backwardsTree.height > fromBottomMaxHeight) {

                //check to see if this is the new tallest from the bottom
                fromBottomMaxHeight = fromBottomMaxHeight.coerceAtLeast(backwardsTree.height)

                //Only increase the visibleCount if this tree wasn't previously visible
                if (!backwardsTree.isVisible) {
                    visibleCount++
                }
                backwardsTree.isVisible = true
            }
        }
    }
    return visibleCount
}
