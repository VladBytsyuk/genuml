package ui.screens.editor

import tornadofx.*


class MenuView : View() {
    override val root = menubar {
        menu(FILE.TITLE) {
            item(FILE.LOAD_FROM_SOURCES).action(::onLoadFromSources)
            item(FILE.CLEAR).action(::onClear)
        }
        menu(EDIT.TITLE) { }
        menu(VIEW.TITLE) { }
    }

    private fun onLoadFromSources() = chooseDirectory(FILE.SELECT_SOURCE_FILES)

    private fun onClear() { /* do nothing */ }
}

private object FILE {
    const val TITLE = "File"
    const val LOAD_FROM_SOURCES = "Load from sources"
    const val CLEAR = "Clear"

    const val SELECT_SOURCE_FILES = "Select source code files"
}

private object EDIT {
    const val TITLE = "Edit"
}

private object VIEW {
    const val TITLE = "View"
}
