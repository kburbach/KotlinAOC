import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println(debugMode: Boolean = true) = debugMode.let {debug ->
    if(debug) println(this)
}

fun Any?.print(debugMode: Boolean = true) = debugMode.let {debug ->
    if(debug) print(this)
}
/**
 * Reads the input as a single String.
 */
fun readText(name: String) = File("src", "$name.txt")
    .readText()