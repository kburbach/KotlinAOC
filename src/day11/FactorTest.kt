package day11

import println

fun main() {

//    Factor(4).println()
//
//    //(4 + [4])
//    //(4 + [4])
//    Factor(4, mutableListOf(Factor(4))).println()
//
//    //(4 + [4, 4])
//    //(4 + [4, 4])
//    Factor(
//        4,
//        mutableListOf(
//            Factor(4),
//            Factor(4)
//        )
//    ).println()
//
//
//    //(4 + [4, (4 + [5])])
//    //(4 + [4, (4 + [5])])
//    Factor(
//        4,
//        mutableListOf(
//            Factor(4),
//            Factor(4, listOf(Factor(5)))
//        )
//    ).println()
//
//    //(1 + [(2 + [3]), (4 + [5])])
//    //(1 + [(2 + [3]), (4 + [5])])
//    Factor(
//        1,
//        listOf(
//            Factor(2, listOf(Factor(3))),
//            Factor(4, listOf(Factor(5)))
//        )
//    ).println()
//
//    //(1 + [(2 + [3, 3, 3]), (4 + [5, 3])])
//    //(1 + [(2 + [3, 3, 3]), (4 + [5, 3])])
//    Factor(
//        1,
//        listOf(
//            Factor(2, listOf(Factor(3), Factor(3), Factor(3))),
//            Factor(4, listOf(Factor(5), Factor(3)))
//        )
//    ).println()
//
//    // [(2 + [3, 3, 3]), (4 + [5, 3])]
//    // [(2 + [3, 3, 3]), (4 + [5, 3])]
//    Factor(
//        0,
//        listOf(
//            Factor(2, listOf(Factor(3), Factor(3), Factor(3))),
//            Factor(4, listOf(Factor(5), Factor(3)))
//        )
//    ).println()
//
//    // [(2 + [3 * 3 * 3]) * [5 * 3]]
//    // [(2 + [3 * 3 * 3]) * [5 * 3]]
//    Factor(
//        0,
//        listOf(
//            Factor(2, listOf(Factor(3), Factor(3), Factor(3))),
//            Factor(0, listOf(Factor(5), Factor(3)))
//        )
//    ).println()
//
////    [[2 * 3] * (1 + [(1 + [3 * 4])])]
//    //[[2 * 3] * (1 + [(1 + [3 * 4])])]
//    val f =
//////        0,
//////        listOf(
//////            Factor(0, listOf(Factor(2), Factor(3))),
//////            Factor(1, listOf(Factor(1, listOf()))
//////        )
////
//        ((Factor(2) * Factor(3)) * (1+(1+(Factor(3) * Factor(4))))).also {
//            it.println()
//            "Expecting ${2 * 3 * (1 + (1 + (3 * 4)))}".println()
//            it.totalValue.println()
//        }
//    //84
//    listOf(7, 14, 2, 3, 5, 10, 11).forEach {
//        f.rem(it).println()
//    }
//
//
//    val f = (
//            Factor(7, listOf(1))
//
//                    *
//
//            Factor(1, listOf(2, 3))
//
//            )
//
//
//    f.rem(5).println()
//    f.totalValue.also { "Total value is $it".println() }.rem(5).println()
}
//operator fun Int.plus(other: Factor) = other.plus(this)

