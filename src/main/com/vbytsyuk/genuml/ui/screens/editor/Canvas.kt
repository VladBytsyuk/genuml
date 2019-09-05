package ui.screens.editor

import com.vbytsyuk.genuml.domain.Model
import javafx.beans.Observable
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import tornadofx.*


class CanvasView : View() {
    private val controller: CanvasController by inject()

    override val root = form {
        canvas(WIDTH, HEIGHT) {
            controller.elementsProperty.addListener { _: Observable ->
                renderElements(controller.elements)
            }
        }
    }

}

class CanvasController : Controller() {
    val elementsProperty = SimpleObjectProperty<List<String>>()
    val elements: List<String> by elementsProperty

    fun renderModel(model: Model) {
        runAsync {
            FXCollections.observableArrayList(model.elements.map { "${it.name} [${it.type}]" })
        } ui { elementsProperty.value = it }
    }

    fun clear() {
        runAsync { /* do nothing */ } ui {
            elementsProperty.value = ArrayList()
        }
    }
}

@SuppressWarnings("MagicNumber")
fun Canvas.renderElements(elements: List<String>?) {
    val context = graphicsContext2D
    context.clearRect(0.0, 0.0, WIDTH, HEIGHT)
    context.fill = Color.WHEAT
    context.stroke = Color.BLACK
    context.lineWidth = 2.0
    var x = 0.0
    var y = 40.0
    elements?.forEach { elementName ->
        context.fill = Color.WHEAT
        context.fillRect(x, y, 250.0, 50.0)
        context.fill = Color.BLACK
        context.fillText(elementName, x, y + 20)
        x += 15.0
        y += 55.0
    }

}
