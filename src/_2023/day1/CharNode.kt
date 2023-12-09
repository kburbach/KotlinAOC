package _2023.day1

import println

class CharNode(private val value: Char, private val debug: Boolean) {
    //Make this a sequence?
    var nextChar: CharNode? = null
    private set

    fun addPossibleNextChar(char: Char): CharNode = CharNode(char, debug).also {
        nextChar = it
    }

    override fun toString(): String {
        return value.toString() + (nextChar?.toString() ?: "")
    }

    fun containsSequence(charSequence: CharSequence): Boolean {
        return ((value == charSequence.firstOrNull()).also {match ->
            if(match) {
                "$value matches the start of $charSequence (${charSequence.firstOrNull()})..".println(debug)
            } else {
                "$value doesn't matches the start of $charSequence (${charSequence.firstOrNull()})..".println(debug)
            }
        }) && (nextChar == null ||
            nextChar!!.containsSequence(charSequence.subSequence(1, charSequence.length).also {
                "Checking to see if child ${nextChar!!.value} match subsequence $it".println(debug)
            })
        )
    }
}

class CollectionCharNodes private constructor(list: List<CharNode> = emptyList()) {

    val nodes: MutableList<CharNode>

    init {
        nodes = mutableListOf(*list.toTypedArray())
    }

    fun containsSequence(sequence: CharSequence): CharNode? {
        return nodes.firstOrNull {
            it.containsSequence(sequence)
        }
    }

    companion object {
        fun fromString(string: String, debug: Boolean): CharNode {
            val dummy = CharNode(' ', debug)
            var currentPtr = dummy

            string.toCharArray().forEach {
                currentPtr = currentPtr.addPossibleNextChar(it)
            }

            return dummy.nextChar!!
        }

        fun fromStrings(list: List<String>, debug: Boolean): CollectionCharNodes = CollectionCharNodes().apply {
            list.map {
               fromString(it, debug)
            }.forEach {
                nodes.add(it)
            }
        }
    }
}