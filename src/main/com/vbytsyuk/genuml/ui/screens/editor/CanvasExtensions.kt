package com.vbytsyuk.genuml.ui.screens.editor

import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.paint.Paint


@SuppressWarnings("MagicNumber")
fun Canvas.renderElements(elements: List<String>?) {
    graphicsContext2D.clearRect(0.0, 0.0, WIDTH, HEIGHT)
    var x = 0.0
    var y = 40.0
    val colors = listOf(Color.AQUA, Color.BEIGE, Color.VIOLET, Color.CADETBLUE, Color.YELLOWGREEN)
    elements?.forEach { elementName ->
        drawRect(x, y, width = 250.0, height = 50.0, fillColor = colors.random(), strokeWidth = 1.0)
        drawText(x + 25.0, y + 10, text = elementName)
        x += 15.0
        y += 55.0
    }
}


/* =====================================================================================================================
 * Primitives
 * =====================================================================================================================
 */

fun Canvas.drawRect(
    x: Double, y: Double,
    width: Double, height: Double,
    fillColor: Paint = Color.WHITE, strokeColor: Paint = Color.BLACK,
    strokeWidth: Double = 0.0
) = with (graphicsContext2D) {
    fill = fillColor
    fillRect(x, y, width, height)
    lineWidth = strokeWidth
    stroke = strokeColor
    strokeRect(x, y, width, height)
}

@SuppressWarnings("MagicNumber")
fun Canvas.drawText(x: Double, y: Double, text: String) = with (graphicsContext2D) {
    fill = Color.BLACK
    fillText(text, x, y + 20)
}
