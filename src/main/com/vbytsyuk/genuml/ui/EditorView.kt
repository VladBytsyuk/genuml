package com.vbytsyuk.genuml.ui

import tornadofx.*

class EditorView : View() {

    override val root = vbox {
        menubar {
            menu("File") {
                item("Load from sources") {
                    action {
                        var dir = chooseDirectory("Select source code files")
                    }
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
