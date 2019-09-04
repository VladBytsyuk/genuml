package com.vbytsyuk.genuml.ui.screens.editor

import tornadofx.*
import ui.screens.editor.CanvasView
import ui.screens.editor.HEIGHT
import ui.screens.editor.MenuView
import ui.screens.editor.WIDTH


class EditorWindow : View() {
    override val root = borderpane {
        prefWidth = WIDTH
        prefHeight = HEIGHT
        top<MenuView>()
        center<CanvasView>()
    }
}

class EditorController : Controller() {
    fun showErrors(errors: List<String>) {
        println(errors)
    }
}
