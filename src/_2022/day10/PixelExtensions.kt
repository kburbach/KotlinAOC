package _2022.day10

import print

/**
 * Calculates what pixel should be drawn given:
 * @param pixelPosition current pixel
 * @param spriteCenterPosition and int representing the center of the range [ i-1, i+ 1]
 *
 * @return LitPixel if sprite is positioned at pixelPosition, DarkPixel otherwise
 */
fun getPixelDrawn(pixelPosition: Int, spriteCenterPosition: Int) =
    when (pixelPosition in spriteCenterPosition.toSpriteRange()) {
        true -> LitPixel()
        false -> DarkPixel()
    }

/**
 * Extension function to create the sprite range given an Int center point. Quick way to create a
 * range of [i-1, i+1]
 */
fun Int.toSpriteRange(): IntRange = this - 1..this + 1

/**
 * The expected output is 8 capital letters given a row of 40 characters, so split each row into
 * columns of 5, you'll be able to figure out what the letters are a lot easier
 */
fun List<List<Pixel>>.pixelPrint() = forEach {
    it.chunked(5).forEach { chunk ->
        chunk.joinToString(separator = "", postfix = "  ").print()
    }.also {
        println("")
    }
}