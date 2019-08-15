package ui.screens.editor

import javafx.stage.FileChooser
import tornadofx.*
import java.io.File


class MenuView : View() {
    private val fileController: FileMenuController by inject()

    override val root = menubar {
        menu(FILE.TITLE) {
            item(FILE.LOAD_FROM_SOURCES).action {
                val paths = chooseFile(
                    title = FILE.SELECT_SOURCE_FILES,
                    filters = arrayOf(FileChooser.ExtensionFilter("All files", "*.*"))
                )
                fileController.onLoadFromSources(paths)
            }
            item(FILE.CLEAR).action { fileController.onClear() }
        }
        menu(EDIT.TITLE) { }
        menu(VIEW.TITLE) { }
    }
}

class FileMenuController: Controller() {
    fun onLoadFromSources(paths: List<File>) {
        println(paths)
        //TODO: implement
    }

    fun onClear() { /* do nothing */ }
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
