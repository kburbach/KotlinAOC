package day11

fun getMonkeys(): List<Monkey> {

    val monkey0 = Monkey(
        currentItems = intArrayOf(50, 70, 89, 75, 66, 66),
        inspectionOperation = { times(5) },
        testIsDivisibleBy = 2,
        passedTestMonkey = 2,
        failedTestMonkey = 1
    )

    val monkey1 = Monkey(
        currentItems = intArrayOf(85),
        inspectionOperation = { times(this) },
        testIsDivisibleBy = 7,
        passedTestMonkey = 3,
        failedTestMonkey = 6
    )

    val monkey2 = Monkey(
        currentItems = intArrayOf(66, 51, 71, 76, 58, 55, 58, 60),
        inspectionOperation = { plus(1) },
        testIsDivisibleBy = 13,
        passedTestMonkey = 1,
        failedTestMonkey = 3
    )

    val monkey3 = Monkey(
        currentItems = intArrayOf(79, 52, 55, 51),
        inspectionOperation = { plus(6) },
        testIsDivisibleBy = 3,
        passedTestMonkey = 6,
        failedTestMonkey = 4
    )

    val monkey4 = Monkey(
        currentItems = intArrayOf(69, 92),
        inspectionOperation = { times(17) },
        testIsDivisibleBy = 19,
        passedTestMonkey = 7,
        failedTestMonkey = 5
    )

    val monkey5 = Monkey(
        currentItems = intArrayOf(71, 76, 73, 98, 67, 79, 99),
        inspectionOperation = { plus(8) },
        testIsDivisibleBy = 5,
        passedTestMonkey = 0,
        failedTestMonkey = 2
    )

    val monkey6 = Monkey(
        currentItems = intArrayOf(82, 76, 69, 69, 57),
        inspectionOperation = { plus(7) },
        testIsDivisibleBy = 11,
        passedTestMonkey = 7,
        failedTestMonkey = 4
    )

    val monkey7 = Monkey(
        currentItems = intArrayOf(65, 79, 86),
        inspectionOperation = { plus(5) },
        testIsDivisibleBy = 17,
        passedTestMonkey = 5,
        failedTestMonkey = 0
    )

    return listOf(
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
        currentItems = intArrayOf(79, 98),
        inspectionOperation = { times(19) },
        testIsDivisibleBy = 23,
        passedTestMonkey = 2,
        failedTestMonkey = 3
    )

    val monkey1 = Monkey(
        currentItems = intArrayOf(54, 65, 75, 74),
        inspectionOperation = { plus(6) },
        testIsDivisibleBy = 19,
        passedTestMonkey = 2,
        failedTestMonkey = 0
    )

    val monkey2 = Monkey(
        currentItems = intArrayOf(79, 60, 97),
        inspectionOperation = { times(this) },
        testIsDivisibleBy = 13,
        passedTestMonkey = 1,
        failedTestMonkey = 3
    )

    val monkey3 = Monkey(
        currentItems = intArrayOf(74),
        inspectionOperation = { plus(3) },
        testIsDivisibleBy = 17,
        passedTestMonkey = 0,
        failedTestMonkey = 1
    )

    return listOf(
        monkey0,
        monkey1,
        monkey2,
        monkey3
    )
}