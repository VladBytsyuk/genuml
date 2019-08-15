package com.vbytsyuk.genuml.ui.screens.editor

import tornadofx.*
import ui.screens.editor.CanvasView
import ui.screens.editor.MenuView


class EditorWindow : View() {
    override val root = borderpane {
        top<MenuView>()
        center<CanvasView>()
    }
}
