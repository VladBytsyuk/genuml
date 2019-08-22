package ui.screens.editor

import com.vbytsyuk.genuml.domain.Model
import tornadofx.*


class CanvasView : View() {
    override val root = form {
        canvas {
            width = Size.WIDTH
            height = Size.HEIGHT
        }
    }
}

private object Size {
    const val WIDTH = 640.0
    const val HEIGHT = 480.0
}


class CanvasController : Controller() {
    fun renderModel(model: Model) {
        println(model)
    }
}
