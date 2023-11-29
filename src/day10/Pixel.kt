package day10

sealed class Pixel(open val s: String) {
    override fun toString() = s // so this literally does nothing?
}

data class LitPixel(override val s: String = "#") : Pixel(s) {

    //without this (useless?) override, LitPixel doesn't inherit Pixel.toString()...
    override fun toString() = super.toString()
}

data class DarkPixel(override val s: String = ".") : Pixel(s) {
    override fun toString() = super.toString()
}