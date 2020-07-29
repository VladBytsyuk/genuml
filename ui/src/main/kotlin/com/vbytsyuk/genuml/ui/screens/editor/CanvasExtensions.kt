package com.vbytsyuk.genuml.ui.screens.editor

import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.VisibilityModifier
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import javafx.scene.paint.Paint


@SuppressWarnings("MagicNumber")
fun Canvas.renderElements(elements: List<Element>?) {
    graphicsContext2D.clearRect(0.0, 0.0, WIDTH, HEIGHT)
    val x = 0.0
    var y = 40.0
    elements?.forEach { element ->
        drawRect(x, y, width = 450.0, height = 250.0, fillColor = element.color, strokeWidth = 1.0)
        drawText(x + 25.0, y + 10, text = element.name)
        y += 50.0
        element.properties.forEach { property ->
            val visibility = when (property.visibilityModifier) {
                VisibilityModifier.PRIVATE -> '-'
                VisibilityModifier.PUBLIC -> '+'
                VisibilityModifier.PROTECTED -> '#'
                VisibilityModifier.INTERNAL -> '~'
            }
            drawText(x + 25.0, y + 10, text = "$visibility\t${property.name}: ${property.type}")
            y += 25.0
        }
        y += 25.0
        element.methods.forEach { meth ->
            val visibility = when (meth.visibilityModifier) {
                VisibilityModifier.PRIVATE -> '-'
                VisibilityModifier.PUBLIC -> '+'
                VisibilityModifier.PROTECTED -> '#'
                VisibilityModifier.INTERNAL -> '~'
            }
            val args = meth.arguments.joinToString(separator = ", ") { "${it.name}: ${it.type}" }
            drawText(x + 25.0, y + 10, text = "$visibility\t${meth.name}($args): ${meth.returnType}")
            y += 25.0
        }
        y += 250.0
    }
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
