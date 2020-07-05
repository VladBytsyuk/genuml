package com.vbytsyuk.genuml.ui

import com.vbytsyuk.genuml.ui.screens.editor.EditorWindow
import tornadofx.*

class GenUmlApplication : App(EditorWindow::class) {
    companion object {
        fun launch(args: Array<String>) = launch<GenUmlApplication>(args)
    }
}
