//package day11
//
//import print
//import println
//
//class Monkey(
//    vararg currentItems: Int,
//    val testIsDivisibleBy: Int,
//    private val inspectionOperation: (Factor).() -> Factor,
//    val passedTestMonkey: Int,
//    val failedTestMonkey: Int
//) : Iterator<Factor> {
//    var numInspectionsPerformed = 0
//        private set
//
//    private val testOperation: (Factor).() -> Boolean = {
//        rem(testIsDivisibleBy) == 0
//    }
//
//    private val currentItems = ArrayDeque(elements = currentItems.map(::Factor))
//
//    fun inspectNextItem(factor: Factor, debug: Boolean): Pair<Factor, Int> {
////        val newFactor = factor.inspectionOperation()//.gotBoredOperation()
////        val total = newFactor.totalValue
//        if(factor.toString() == "(3 + [[(6 + [(3 + [[(6 + [(3 + [[(6 + [(3 + [[(6 + [(3 + [[(6 + [(3 + [[(6 + [(3 + [[1198437724 * 1198437724]])]) * 19]])]) * 19]])]) * 19]])]) * 19]])]) * 19]])]) * 19]])"){
//            "WTF!!".println()
//        }
//        return factor.inspectionOperation().let { factorAfterOperation ->
//            val testPassed = factorAfterOperation.testOperation()
//            when (testPassed) {
//                true -> passedTestMonkey
//                false -> failedTestMonkey
//            }.let { monkeyIndex ->
////                " --> Inspecting $factor...".print(debug || testPassed)
////                "new Factor is $factorAfterOperation".println(debug || testPassed)
////                "      --> which is ${if (!testPassed) "not" else ""} divisible by $testIsDivisibleBy".println(debug || testPassed)
//
//                if (testPassed) {
//                    val temp = factorAfterOperation / testIsDivisibleBy
//                    Factor(
//                        factors = listOf(
//                            Factor(testIsDivisibleBy),
//                            Factor(factorAfterOperation / testIsDivisibleBy)
//                        ),
//                        disBoiTooBig = (Int.MAX_VALUE / temp)  < testIsDivisibleBy
//                    ).also {
//                        "$factorAfterOperation is divisible by $testIsDivisibleBy, so creating $it".println()
//                    }
//                } else {
//                    factorAfterOperation
//                }.let { newFactor ->
//                    " --> Sending $newFactor to monkey$monkeyIndex".println(debug || testPassed)
//
//                    newFactor to monkeyIndex
//                }
//
//
//            }.also {
//                numInspectionsPerformed++
//                "    --> now inspected $numInspectionsPerformed items".println(debug)
//            }
//        }
//    }
////    fun inspectNextItem(worryLevel: Int): Pair<Int, Int>  = 1 to 1
////        val newWorryLevel = worryLevel.inspectionOperation()
////        return when (worryLevel.testOperation()) {
////            true -> newWorryLevel to passedTestMonkey
////            false -> newWorryLevel to failedTestMonkey
////        }
////    }
//
//    fun receiveItem(item: Factor) = currentItems.addLast(item)
//    fun items() = currentItems.toList()
//
//    companion object {
//        //        val gotBoredOperation: (Factor).() -> Factor = {
//////            Factor(totalValue.floorDiv(3))
////        }
//        val keepWorryLevelsManageable: (Int).() -> Int = {
//            this
//        }
//    }
//
//    override fun hasNext() = currentItems.isNotEmpty()
//    override fun next() = currentItems.removeFirst()
//}