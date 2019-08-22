package ui.screens.editor

import com.vbytsyuk.genuml.domain.Model
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*


class CanvasView : View() {
    private val controller: CanvasController by inject()

    override val root = vbox {
        label("My elements")
        listview(controller.observableList)
    }

}

class CanvasController : Controller() {
    val observableList: ObservableList<String> = FXCollections.observableArrayList()

    fun renderModel(model: Model) {
        observableList.clear()
        observableList.addAll(model.elements.map { "${it.name} [${it.type}]" })
    }
}
