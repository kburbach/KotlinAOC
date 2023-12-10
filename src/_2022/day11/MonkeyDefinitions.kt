package _2022.day11

import Monkey

fun getMonkeys(): List<Monkey> {

    val monkey0 = Monkey(
        items = mutableListOf<Long>(50, 70, 89, 75, 66, 66),
        operation = { it.times(5) },
        test = 2,
        trueMonkey = 2,
        falseMonkey = 1
    )

    val monkey1 = Monkey(
        items = mutableListOf<Long>(85),
        operation = { it.times(it) },
        test = 7,
        trueMonkey = 3,
        falseMonkey = 6
    )

    val monkey2 = Monkey(
        items = mutableListOf<Long>(66, 51, 71, 76, 58, 55, 58, 60),
        operation = { it.plus(1) },
        test = 13,
        trueMonkey = 1,
        falseMonkey = 3
    )

    val monkey3 = Monkey(
        items = mutableListOf<Long>(79, 52, 55, 51),
        operation = { it.plus(6) },
        test = 3,
        trueMonkey = 6,
        falseMonkey = 4
    )

    val monkey4 = Monkey(
        items = mutableListOf<Long>(69, 92),
        operation = { it.times(17) },
        test = 19,
        trueMonkey = 7,
        falseMonkey = 5
    )

    val monkey5 = Monkey(
        items = mutableListOf<Long>(71, 76, 73, 98, 67, 79, 99),
        operation = { it.plus(8) },
        test = 5,
        trueMonkey = 0,
        falseMonkey = 2
    )

    val monkey6 = Monkey(
        items = mutableListOf<Long>(82, 76, 69, 69, 57),
        operation = { it.plus(7) },
        test = 11,
        trueMonkey = 7,
        falseMonkey = 4
    )

    val monkey7 = Monkey(
        items = mutableListOf<Long>(65, 79, 86),
        operation = { it.plus(5) },
        test = 17,
        trueMonkey = 5,
        falseMonkey = 0
    )

    return mutableListOf(
        monkey0,
        monkey1,
        monkey2,
        monkey3,
        monkey4,
        monkey5,
        monkey6,
        monkey7,
    )
}

fun getTestMonkeys(): List<Monkey> {
    val monkey0 = Monkey(
        items = mutableListOf<Long>(79, 98),
        operation = { it.times(19) },
        test = 23,
        trueMonkey = 2,
        falseMonkey = 3
    )

    val monkey1 = Monkey(
        items = mutableListOf<Long>(54, 65, 75, 74).toMutableList(),
        operation = { it.plus(6) },
        test = 19,
        trueMonkey = 2,
        falseMonkey = 0
    )

    val monkey2 = Monkey(
        items = mutableListOf<Long>(79, 60, 97).toMutableList(),
        operation = { it.times(it) },
        test = 13,
        trueMonkey = 1,
        falseMonkey = 3
    )

    val monkey3 = Monkey(
        items = mutableListOf<Long>(74).toMutableList(),
        operation = { it.plus(3) },
        test = 17,
        trueMonkey = 0,
        falseMonkey = 1
    )

    return mutableListOf(
        monkey0,
        monkey1,
        monkey2,
        monkey3
    )
}