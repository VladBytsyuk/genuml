package com.vbytsyuk.genuml.parsers

import com.vbytsyuk.genuml.controllers.ISourceCodeReader
import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.Coordinate
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.usecases.addElement


const val INTERFACE = "interface"
const val CLASS = "class"
const val FINAL_CLASS = "final class"
const val OPEN_CLASS = "open class"
const val ABSTRACT_CLASS = "abstract class"
const val ENUM_CLASS = "enum class"

class KotlinParser(sourceCodeReader: ISourceCodeReader) : Parser(sourceCodeReader) {
    override val extension = "kt"

    override fun parseFile(sourceCodeLines: List<String>): Result {sourceCodeLines
        val elementDeclarationLines =
            sourceCodeLines.filter { it.contains("class ") || it.contains("interface ") }
        if (elementDeclarationLines.isEmpty()) {
            return Result.Error("File doesn't contain elements declarations")
        }

        val model = Model()
        elementDeclarationLines.forEach { line ->
            line.elementDeclaration?.let { (type, name) ->
                model.addElement(type = type, coordinate = Coordinate(x = 0, y = 0))
                    .apply { this.name = name }
            }
        }
        return Result.Success(model)
    }

    private val String.elementDeclaration: Pair<Element.Type, String>?
        get() = when {
            contains(INTERFACE) -> Element.Type.INTERFACE to getWordAfter(word = INTERFACE)
            contains(OPEN_CLASS) -> Element.Type.OPEN_CLASS to getWordAfter(word = CLASS)
            contains(FINAL_CLASS) -> Element.Type.FINAL_CLASS to getWordAfter(word = CLASS)
            contains(ABSTRACT_CLASS) -> Element.Type.ABSTRACT_CLASS to getWordAfter(word = CLASS)
            contains(ENUM_CLASS) -> Element.Type.ENUM_CLASS to getWordAfter(word = CLASS)
            else -> null
        }

    private fun String.getWordAfter(word: String): String {
        val words = split(" ")
        val wordIndex = words.indexOf(word)
        return when {
            wordIndex + 1 in 0 until words.size -> words[wordIndex + 1]
            else -> ""
        }
    }
}
