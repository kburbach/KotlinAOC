package day11

import println

fun main() {

    Factor(4).println()

    //(4 + [4])
    //(4 + [4])
    Factor(4, mutableListOf(Factor(4))).println()

    //(4 + [4, 4])
    //(4 + [4, 4])
    Factor(4,
        mutableListOf(
            Factor(4),
            Factor(4)
        )
    ).println()


    //(4 + [4, (4 + [5])])
    //(4 + [4, (4 + [5])])
    Factor(
        4,
        mutableListOf(
            Factor(4),
            Factor(4, listOf(Factor(5)))
        )
    ).println()

    //(1 + [(2 + [3]), (4 + [5])])
    //(1 + [(2 + [3]), (4 + [5])])
    Factor(
        1,
        listOf(
            Factor(2, listOf(Factor(3))),
            Factor(4, listOf(Factor(5)))
        )
    ).println()

    //(1 + [(2 + [3, 3, 3]), (4 + [5, 3])])
    //(1 + [(2 + [3, 3, 3]), (4 + [5, 3])])
    Factor(
        1,
        listOf(
            Factor(2, listOf(Factor(3), Factor(3), Factor(3))),
            Factor(4, listOf(Factor(5), Factor(3)))
        )
    ).println()

    // [(2 + [3, 3, 3]), (4 + [5, 3])]
    // [(2 + [3, 3, 3]), (4 + [5, 3])]
    Factor(
        0,
        listOf(
            Factor(2, listOf(Factor(3), Factor(3), Factor(3))),
            Factor(4, listOf(Factor(5), Factor(3)))
        )
    ).println()

    // [(2 + [3 * 3 * 3]) * [5 * 3]]
    // [(2 + [3 * 3 * 3]) * [5 * 3]]
    Factor(
        0,
        listOf(
            Factor(2, listOf(Factor(3), Factor(3), Factor(3))),
            Factor(0, listOf(Factor(5), Factor(3)))
        )
    ).println()

//    [[2 * 3] * (1 + [(1 + [3 * 4])])]
    //[[2 * 3] * (1 + [(1 + [3 * 4])])]
    Factor(
        0,
        listOf(
            Factor(0, listOf(Factor(2), Factor(3))),
            Factor(1, listOf(Factor(1, listOf(Factor(3), Factor(4)))))
        )
    ).let {
        println()
        "Expecting ${ 2*3*(1 + (1 + (3*4)))}".println()
        it.totalValue.println()
    }

}