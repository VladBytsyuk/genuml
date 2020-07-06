package com.vbytsyuk.genuml.parsers

import com.vbytsyuk.genuml.controllers.ISourceCodeReader
import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.Coordinate
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.usecases.addElement


private const val INTERFACE = "interface"
private const val CLASS = "class"
private const val FINAL_CLASS = "class"
private const val OPEN_CLASS = "open class"
private const val ABSTRACT_CLASS = "abstract class"
private const val ENUM_CLASS = "enum class"

class KotlinParser(sourceCodeReader: ISourceCodeReader) : Parser(sourceCodeReader) {
    override val extension = "kt"

    override fun parseFile(sourceCode: String): Result {
        val lines = sourceCode.split('\n')
        val elementDeclarationLines = lines.filter { it.contains(CLASS) || it.contains(INTERFACE) }

        if (elementDeclarationLines.isEmpty()) return Result.Success(model = Model())

        val model = Model()
        elementDeclarationLines.forEach { line ->
            val (type, name) = line.elementDeclaration ?: return@forEach
            model
                .addElement(type = type, coordinate = Coordinate(x = 0, y = 0))
                .apply { this.name = name }
        }
        return Result.Success(model)
    }

    private val String.elementDeclaration: Pair<Element.Type, String>?
        get() = when {
            contains(INTERFACE) -> Element.Type.INTERFACE to getWordAfter(word = INTERFACE)
            contains(OPEN_CLASS) -> Element.Type.OPEN_CLASS to getWordAfter(word = CLASS)
            contains(ABSTRACT_CLASS) -> Element.Type.ABSTRACT_CLASS to getWordAfter(word = CLASS)
            contains(ENUM_CLASS) -> Element.Type.ENUM_CLASS to getWordAfter(word = CLASS)
            contains(FINAL_CLASS) -> Element.Type.FINAL_CLASS to getWordAfter(word = CLASS)
            else -> null
        }

    private fun String.getWordAfter(word: String): String {
        val words = split(Regex("([ ]|[(])"))
        val wordIndex = words.indexOf(word)
        return when {
            wordIndex + 1 in words.indices -> words[wordIndex + 1]
            else -> ""
        }
    }
}
