package com.vbytsyuk.genuml.ui.screens.editor

import tornadofx.*
import ui.screens.editor.CanvasView
import ui.screens.editor.MenuView


class EditorWindow : View() {
    override val root = vbox {
        this += MenuView()
        this += CanvasView()
    }
}
