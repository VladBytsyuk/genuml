package com.vbytsyuk.genuml.ui.screens.editor

import tornadofx.*


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
