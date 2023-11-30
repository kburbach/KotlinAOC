package day11

import println

open class Factor(
    private val value: Int = 0,
    private val factors: List<Factor> = listOf()
) {

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

    val totalValue by lazy {
        calculateValue()
    }

    private fun calculateValue(): Int {
        val childrenVal =
            factors.takeIf { it.isNotEmpty() }?.fold(1) { acc, next ->
                val nextVal = acc * next.calculateValue()
                nextVal
            } ?: 0

//        "looking at factor $this...".println()
//        "Children Value was $childrenVal, the sum value is $value for a total of ${childrenVal + value}".println()

        return childrenVal + value
    }

    //Say this factor is Factor(4):
    // - adding i to it will make it Factor(4+i)
    //Say this factor was Factor(4, listOf(Factor(4), Factor(5)) -- this is basically 4 + (4*5)
    // -- adding i to this will return Factor(i, Factor(4, listOf(Factor(4), Factor(5)))
    operator fun plus(i: Int) = if (factors.isEmpty()) { // just a number basically
        Factor(i + value)
    } else { //if we have other factors aka 1+(4*5), we can't just add to the sum, we need to create a whole new factor
        Factor(i, factors = listOf(this))
    }

    operator fun times(i: Int) = Factor(factors = listOf(Factor(i), Factor(value, factors)))


    operator fun times(other: Factor) = Factor(factors = listOf(this, other))

}