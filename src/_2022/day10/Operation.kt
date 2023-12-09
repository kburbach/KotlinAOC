package _2022.day10

sealed class Operation {

    data class Addx(val value: Int) : Operation()
    data object Noop : Operation()

    companion object {
        fun fromString(s: String): Operation? {

            val parts = s.split(" ", limit = 2)

            val cmd = parts[0]
            val value = parts.getOrNull(1)?.toInt()

            return when (cmd) {
                "addx" -> value?.let { Addx(it) }
                "noop" -> Noop
                else -> null
            }
        }
    }
}