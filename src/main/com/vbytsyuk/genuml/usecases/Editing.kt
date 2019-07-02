package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.Coordinate
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Reference


val Element.borders: Map<IntRange, IntRange>
    get() {
        val x = presentation.coordinate.x
        val y = presentation.coordinate.y
        val width = presentation.style.size.width
        val height = presentation.style.size.height

        val leftBorder = x..x to y..y + height
        val topBorder = x..x + width to y..y
        val rightBorder = x + width..x + width to y..y + height
        val bottomBorder = x..x + width to y + height..y + height

        return mapOf(leftBorder, topBorder, rightBorder, bottomBorder)
    }

fun Coordinate.isOnBorderOf(element: Element): Boolean =
    element.borders.any { (xRange, yRange) -> x in xRange && y in yRange }

fun Coordinate.notOnBorderOf(element: Element): Boolean = !isOnBorderOf(element)


fun Reference.reconnect(
    newSource: Element = source, newStart: Coordinate = presentation.start,
    newTarget: Element = target, newEnd: Coordinate = presentation.end
) {
    if (newStart.notOnBorderOf(newSource) || newEnd.notOnBorderOf(newTarget)) {
        throw IllegalArgumentException("Wrong coordinates of reference reconnect. It should be on element border.")
    }

    source = newSource
    target = newTarget

    presentation.start = newStart
    presentation.end = newEnd
}
