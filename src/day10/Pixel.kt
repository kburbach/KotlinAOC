package day10

sealed class Pixel(private val s: String) {
    final override fun toString() = s
}

class LitPixel : Pixel("#")
class DarkPixel : Pixel(".")