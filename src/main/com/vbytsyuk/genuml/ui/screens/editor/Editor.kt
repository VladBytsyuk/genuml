package com.vbytsyuk.genuml.ui.screens.editor

import tornadofx.*
import ui.screens.editor.CanvasView
import ui.screens.editor.MenuView


class EditorWindow : View() {
    override val root = borderpane {
        prefWidth = 1280.0
        prefHeight = 720.0
        top<MenuView>()
        center<CanvasView>()
    }
}

class EditorController : Controller() {
    fun showErrors(errors: List<String>) {
        println(errors)
    }
}
