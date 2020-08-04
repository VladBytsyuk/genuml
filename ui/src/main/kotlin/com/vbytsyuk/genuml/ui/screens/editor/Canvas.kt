package com.vbytsyuk.genuml.ui.screens.editor

import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.ui.onChange
import com.vbytsyuk.genuml.ui.toObservableList
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*


class CanvasView : View() {
    private val controller: CanvasController by inject()

    override val root = form {
        canvas(WIDTH, HEIGHT) {
            controller.elementsProperty.onChange { renderElements(controller.elements) }
        }
    }
}

class CanvasController : Controller() {
    val elementsProperty = SimpleObjectProperty<List<Element>>()
    val elements: List<Element> by elementsProperty


    fun renderModel(model: Model) = runAsync {
        model.elements.toObservableList()
    } ui { elementsProperty.value = it }

    fun clear() = runAsync {
        /* do nothing */
    } ui { elementsProperty.value = ArrayList() }
}
