package com.vbytsyuk.genuml

import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.parsers.KotlinParser
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import org.junit.Test
import kotlin.test.assertEquals


@Suppress("FunctionMaxLength", "TooManyFunctions", "UnsafeCast")
class KotlinParserTest {

    companion object {
        private const val DIRECTORY = "src/assets/kotlin_parser"
        const val EMPTY_FILE = "$DIRECTORY/Empty.kt"
        const val ONE_INTERFACE = "$DIRECTORY/Factory.kt"
        const val ONE_FINAL_CLASS = "$DIRECTORY/Single.kt"
    }

    private fun test(paths: List<String>, block: (Parser.Result) -> Unit) {
        val parser = KotlinParser(SourceCodeReader())
        val result = parser.parse(paths)
        block(result)
    }

    @Test
    fun `0 source files, 0 elements`() = test(paths = emptyList()) { result ->
        val actualModel = (result as Parser.Result.Success).model
        assertEquals(actual = actualModel, expected = Model())
    }

    @Test
    fun `1 source file, 0 elements`() = test(paths = listOf(EMPTY_FILE)) { result ->
        val actualModel = (result as Parser.Result.Success).model
        assertEquals(actual = actualModel, expected = Model())
    }

    @Test
    fun `1 source file, 1 interface`() = test(paths = listOf(ONE_INTERFACE)) { result ->
        val actualModel = (result as Parser.Result.Success).model
        assertEquals(actual = actualModel.references.size, expected = 0)
        assertEquals(actual = actualModel.elements.size, expected = 1)
        val element = actualModel.elements.first()
        assertEquals(actual = element.name, expected = "Factory")
        assertEquals(actual = element.type, expected = Element.Type.INTERFACE)
    }

    @Test
    fun `1 source file, 1 final class`() = test(paths = listOf(ONE_FINAL_CLASS)) { result ->
        val actualModel = (result as Parser.Result.Success).model
        assertEquals(actual = actualModel.references.size, expected = 0)
        assertEquals(actual = actualModel.elements.size, expected = 1)
        val element = actualModel.elements.first()
        assertEquals(actual = element.name, expected = "LastManOnEarth")
        assertEquals(actual = element.type, expected = Element.Type.FINAL_CLASS)
    }

    @Test
    fun `1 source file, 1 open class`() {
        //TODO: implement
    }

    @Test
    fun `1 source file, 1 abstract class`() {
        //TODO: implement
    }

    @Test
    fun `1 source file, 1 enum class`() {
        //TODO: implement
    }

    @Test
    fun `1 source file, 2 same classes`() {
        //TODO: implement
    }

    @Test
    fun `1 source file, 2 different classes`() {
        //TODO: implement
    }

    @Test
    fun `2 source files, 1 open class`() {
        //TODO: implement
    }

    @Test
    fun `2 source files, 2 different classes`() {
        //TODO: implement
    }


    @Test
    fun `2 source files, 3 different classes`() {
        //TODO: implement
    }

    @Test
    fun `2 source files, 4 different classes`() {
        //TODO: implement
    }
}
