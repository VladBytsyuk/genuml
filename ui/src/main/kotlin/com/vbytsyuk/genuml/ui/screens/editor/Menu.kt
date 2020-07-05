package com.vbytsyuk.genuml.ui.screens.editor

import com.vbytsyuk.genuml.controllers.ParseController
import javafx.scene.control.MenuBar
import javafx.stage.FileChooser
import org.koin.core.KoinComponent
import tornadofx.*
import com.vbytsyuk.genuml.ui.injectKoin
import java.io.File


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


class MenuView : View(), KoinComponent {
    private val parseController: ParseController by injectKoin()
    private val fileController: FileMenuController by inject()

    override val root = menubar {
        fileMenu()
        editMenu()
        viewMenu()
    }

    private fun MenuBar.fileMenu() = menu(FILE.TITLE) {
        item(FILE.LOAD_FROM_SOURCES).action {
            val chooserFilters = arrayOf(
                FileChooser.ExtensionFilter("All files", parseController.extensionsList.map { "*.$it" })
            )
            val files = chooseFile(
                title = FILE.SELECT_SOURCE_FILES,
                filters = chooserFilters,
                mode = FileChooserMode.Multi
            )
            fileController.onLoadFromSources(files)
        }
        item(FILE.CLEAR).action { fileController.onClear() }
    }

    private fun MenuBar.editMenu() = menu(EDIT.TITLE) { /* empty implementation */ }

    private fun MenuBar.viewMenu() = menu(VIEW.TITLE) { /* empty implementation */ }
}

class FileMenuController : Controller(), KoinComponent {
    private val parseController: ParseController by injectKoin()
    private val editorController: EditorController by inject()
    private val canvasController: CanvasController by inject()


    fun onLoadFromSources(files: List<File>) = runAsync {
        parseController.parseFiles(paths = files.map { it.path })
    } ui { parsingResult ->
        when (parsingResult) {
            is ParseController.Result.Success -> canvasController.renderModel(parsingResult.model)
            is ParseController.Result.Error -> editorController.showErrors(parsingResult.errors)
        }
    }

    fun onClear() =
        canvasController.clear()
}
