package com.vbytsyuk.genuml.ui.screens.editor

import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.VisibilityModifier
import com.vbytsyuk.genuml.domain.VisibilityModifier.*
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.text.Font


@SuppressWarnings("MagicNumber")
fun Canvas.renderElements(elements: List<Element>?) {
    data class TextWithCoordinates(val text: String, val x: Double, val y: Double)

    graphicsContext2D.clearRect(0.0, 0.0, WIDTH, HEIGHT)
    val x = 0.0
    var y = 40.0
    elements?.forEach { element ->
        val startY = y
        val textsHolder = mutableListOf<TextWithCoordinates>()
        textsHolder.add(TextWithCoordinates(element.name, x + MARGIN, y + MARGIN))
        y += 2 * MARGIN
        element.propertiesTexts.forEach { text ->
            textsHolder.add(TextWithCoordinates(text, x + MARGIN, y + MARGIN))
            y += MARGIN
        }
        y += MARGIN
        element.methodsTexts.forEach { text ->
            textsHolder.add(TextWithCoordinates(text, x + MARGIN, y + MARGIN))
            y += MARGIN
        }
        y += MARGIN
        val maxLength = (listOf(element.name) + element.propertiesTexts + element.methodsTexts)
            .map { it.length }.max()!!
        drawRect(
            x, startY,
            width = maxLength * FONT_SIZE, height = y - startY,
            fillColor = element.color, strokeWidth = 1.0
        )
        textsHolder.forEach { (text, x, y) -> drawText(x, y, text) }
        y += MARGIN
    }
}

private val Element.propertiesTexts: List<String> get() = properties.map { property ->
    "${property.visibilityModifier.symbol}\t${property.name}: ${property.type}"
}

private val Element.methodsTexts get() = methods.map { method ->
    val args = method.arguments.joinToString(separator = ", ") { "${it.name}: ${it.type}" }
    "${method.visibilityModifier.symbol}\t${method.name}($args): ${method.returnType}"
}

private val VisibilityModifier.symbol get() = when (this) {
    PRIVATE -> '-'
    PUBLIC -> '+'
    PROTECTED -> '#'
    INTERNAL -> '~'
}

private val Element.color: Color get() = when (this.type) {
    Element.Type.INTERFACE -> Color.MEDIUMSEAGREEN
    Element.Type.FINAL_CLASS -> Color.CORNFLOWERBLUE
    Element.Type.OPEN_CLASS -> Color.AQUA
    Element.Type.ABSTRACT_CLASS -> Color.BURLYWOOD
    Element.Type.ENUM_CLASS -> Color.BLUEVIOLET
}

/* =====================================================================================================================
 * Primitives
 * =====================================================================================================================
 */

fun Canvas.drawRect(
    x: Double,
    y: Double,
    width: Double,
    height: Double,
    fillColor: Paint = Color.WHITE,
    strokeColor: Paint = Color.BLACK,
    strokeWidth: Double = 0.0
) = with(graphicsContext2D) {
    fill = fillColor
    fillRect(x, y, width, height)
    lineWidth = strokeWidth
    stroke = strokeColor
    strokeRect(x, y, width, height)
}

const val MARGIN = 36.0
const val FONT_SIZE = 24.0

@SuppressWarnings("MagicNumber")
fun Canvas.drawText(x: Double, y: Double, text: String) = with(graphicsContext2D) {
    fill = Color.BLACK
    font = Font.font(FONT_SIZE)
    fillText(text, x, y + 20)
}
