package day11

import day7.i
import print
import println

open class Factor(
    private val value: Int = 0,
    private val factors: List<Factor> = listOf(),
    private val disBoiTooBig: Boolean = false,
    private val debug: Boolean = false
) {
    //key is the modulo value, value is this factor mod key
    //i.e (5, this.rem(5))
    // makes this shit much faster
    private val modMap = mutableMapOf<Int, Int>()

    //Dis boi naughty
    val totalValue by lazy {
        calculateValue()
    }

//    private var disBoiTooBig = false

    private fun calculateValue(): Int {
        val childrenVal = factors.takeIf { it.isNotEmpty() }?.fold(1) { acc, next ->
            val nextVal = StrictMath.multiplyExact(acc, next.calculateValue())
            nextVal
        } ?: 0

        //        "looking at factor $this...".println()
        //        "Children Value was $childrenVal, the sum value is $value for a total of ${childrenVal + value}".println()

        return childrenVal + value
    }

    override fun toString(): String {
        return with(StringBuilder()) {
            if (value != 0) {
                append("(")
            } else {
                append("[")
            }

            append(innerToString(true))

            if (value != 0) {
                append(")")
            } else {
                append("]")
            }
            toString()
        }
    }

    private fun innerToString(skipParens: Boolean = false): String {
        return with(StringBuilder()) {
            if (!skipParens && factors.isNotEmpty()) {
                if (value != 0) {
                    append("(")
                } else {
                    append("[")
                }
            }
            if (value != 0) {
                append(value)
            }
            if (factors.isNotEmpty()) {
                if (value != 0) {
                    append(" + ")
                }
                if (factors.size > 0 && value != 0) {
                    append("[")
                }

                factors.forEachIndexed { i, factor ->
                    append(factor.innerToString())
                    if (i != factors.lastIndex) {

                        append(" * ")

                    }
                }
                if (factors.size > 0 && value != 0) {
                    append("]")
                }
            }
            if (!skipParens && factors.isNotEmpty()) {
                if (value != 0) {
                    append(")")
                } else {
                    append("]")
                }
            }

            toString()
        }
    }

//    operator fun plus(i: Int) = Factor(i, this.factors, disBoiTooBig = true).also {
//            "Adding $i to $this, creating $it".println()
//        } // next factor will overflow too

        //Say this factor is Factor(4):
        //  adding i to it will make it Factor(4+i)
        //Say this factor was Factor(4, listOf(Factor(4), Factor(5)) -- this is basically 4 + (4*5)
        // -- adding i to this will return Factor(i, Factor(4, listOf(Factor(4), Factor(5)))
        operator fun plus(i: Int) = if (factors.isEmpty()) { // just a number basically
                Factor(i + value).also {
                        "Empty: Adding $i to $this to get $it".println(debug)
                    }
            } else { //if we have other factors aka 1+(4*5), we can't just add to the sum, we need to create a whole new factor
                Factor(i, factors = listOf(this), disBoiTooBig).also {
                    "Adding $i to $this, creating $it".println(debug)
                }
            }

    operator fun times(i: Int) = times(Factor(i))

    operator fun times(other: Factor): Factor {
        "Multiplying $other * $this -> sending back ".print(debug)
        return try {
            if (disBoiTooBig) { //Already caught an overflow expection, so don't both trying again
                Factor(factors = listOf(this, other), disBoiTooBig = true).also {
                    it.println(debug)
                }
            } else { //not too big yet, will be soon
                val newTotal = StrictMath.multiplyExact(totalValue, other.totalValue)
                return Factor(newTotal).also {
                    it.println(debug)
                }
            }

        } catch (e: ArithmeticException) { // catch integer overflow
            Factor(factors = listOf(Factor(totalValue), Factor(other.totalValue)), disBoiTooBig = true).also {
                "GOTCHA BITCH!! Tried to combine $this * $other... creating $it instead".println(debug)
            }
        }
    }

    //(a+b)mod z = (a mod z + b mod z) mod z
    //so if b is the factors, i.e. a list of x *y *t, then b mod z is (x mod z * y mod z * t mod z) mod z
    operator fun rem(i: Int): Int {
        if (modMap.containsKey(i)) {
//            "Fast as fuck boiii".println()
            return modMap[i]!!
        } else {
            return if (factors.isEmpty()) {
                value.rem(i)
            } else {
                (value.rem(i) +
                        (factors.fold(1) { acc: Int, factor: Factor ->

                            acc * factor.rem(i)
                        }).rem(i))
                    .rem(i)
            }.also {
                modMap[i] = it
            }
        }

    }

}