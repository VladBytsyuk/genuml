package com.vbytsyuk.genuml.ui

import tornadofx.*

class EditorView : View() {

    override val root = vbox {
        menubar {
            menu("File") {
                item("Load from sources") {
                    action {  }
                }
                item("Clear") {
                    action {  }
                }
            }
        }

        form {

        }
    }
}
