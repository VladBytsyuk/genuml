package com.vbytsyuk.genuml.domain

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val origin get() = Coordinate(0, 0)
    }
}
data class Size(val width: Int, val height: Int)

@Suppress("UnnecessaryAbstractClass") // It will be implemented in the future
abstract class Style {

    @Suppress("UnnecessaryAbstractClass") // It will be implemented in the future
    abstract class Element {
        abstract var size: Size
        abstract var colorBackground: HexCode
        abstract var colorBorder: HexCode
        abstract var fontColor: HexCode
        abstract var fontSize: Px
    }

    @Suppress("UnnecessaryAbstractClass") // It will be implemented in the future
    abstract class Arrow {
        abstract var color: HexCode
        abstract var colorStroke: HexCode
        abstract var fontColor: HexCode
        abstract var fontSize: Px
        abstract var thickness: Px
    }
}
