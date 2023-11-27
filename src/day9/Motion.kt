package day9

/**
 * Data class to represent a movement instruction
 * @param direction the Direction that the motion is in
 * @param amount    int that represent how far to move
 */
data class Motion(val direction: Direction, val amount: Int) {

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    companion object {

        private const val rightDirString = "R"
        private const val leftDirString = "L"
        private const val upDirString = "U"
        private const val downDirString = "D"

        fun tryParseMotion(input: String): Motion? {
            input.split(" ", limit = 2).let {
                val direction = when (it.first()) {
                    rightDirString -> Direction.RIGHT
                    leftDirString -> Direction.LEFT
                    upDirString -> Direction.UP
                    downDirString -> Direction.DOWN
                    else -> null
                }
                val amount = it.last().toIntOrNull()

                return if (direction == null || amount == null) {
                    null
                } else {
                    Motion(direction, amount)
                }
            }
        }
    }
}