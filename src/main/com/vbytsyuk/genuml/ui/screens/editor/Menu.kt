package ui.screens.editor

import javafx.scene.control.MenuBar
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File


class MenuView : View() {
    private val fileController: FileMenuController by inject()

    override val root = menubar {
        fileMenu()
        editMenu()
        viewMenu()
    }

    private fun MenuBar.fileMenu() = menu(FILE.TITLE) {
        item(FILE.LOAD_FROM_SOURCES).action {
            val paths = chooseFile(
                title = FILE.SELECT_SOURCE_FILES,
                filters = arrayOf(FileChooser.ExtensionFilter("All files", "*.*"))
            )
            fileController.onLoadFromSources(paths)
        }
        item(FILE.CLEAR).action { fileController.onClear() }
    }

    private fun MenuBar.editMenu() = menu(EDIT.TITLE) { /* empty implementation */ }

    private fun MenuBar.viewMenu() = menu(VIEW.TITLE) { /* empty implementation */ }
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
