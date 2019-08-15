package ui.screens.editor

import tornadofx.*


class CanvasView : VIEW() {
    override val root = form {
        canvas {
            width = 640.0
            height = 480.0
        }
    }
}
