package com.vbytsyuk.genuml

import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Element.Type.FINAL_CLASS
import com.vbytsyuk.genuml.domain.Element.Type.OPEN_CLASS
import com.vbytsyuk.genuml.domain.Element.Type.INTERFACE
import com.vbytsyuk.genuml.domain.Element.Type.ABSTRACT_CLASS
import com.vbytsyuk.genuml.domain.Element.Type.ENUM_CLASS
import com.vbytsyuk.genuml.parsers.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


@Suppress("FunctionMaxLength", "TooManyFunctions", "UnsafeCast")
class KotlinParserTest {

    @Test
    fun `1 source file with wrong extension`() = test(
        sourceFilePaths = listOf(JAVA_CLASS_A)
    )

    @Test
    fun `2 source files with wrong extensions`() = test(
        sourceFilePaths = listOf(JAVA_CLASS_A, JAVA_CLASS_B)
    )

    @Test
    fun `2 source files, one with wrong extension`() = test(
        sourceFilePaths = listOf(JAVA_CLASS_A, ONE_FINAL_CLASS),
        parsedElements = mapOf(LAST_MAN_ON_EARTH to FINAL_CLASS)
    )


    @Test
    fun `0 source files, 0 elements`() = test(
        sourceFilePaths = emptyList(),
        parsedElements = emptyMap()
    )

    @Test
    fun `1 source file, 0 elements`() = test(
        sourceFilePaths = listOf(EMPTY_FILE),
        parsedElements = emptyMap()
    )

    @Test
    fun `1 source file, 1 interface`() = test(
        sourceFilePaths = listOf(ONE_INTERFACE),
        parsedElements = mapOf(FACTORY to INTERFACE)
    )

    @Test
    fun `1 source file, 1 final class`() = test(
        sourceFilePaths = listOf(ONE_FINAL_CLASS),
        parsedElements = mapOf(LAST_MAN_ON_EARTH to FINAL_CLASS)
    )

    @Test
    fun `1 source file, 1 open class`() = test(
        sourceFilePaths = listOf(ONE_OPEN_CLASS),
        parsedElements = mapOf(STORE to OPEN_CLASS)
    )

    @Test
    fun `1 source file, 1 abstract class`() = test(
        sourceFilePaths = listOf(ONE_ABSTRACT_CLASS),
        parsedElements = mapOf(GAME to ABSTRACT_CLASS)
    )

    @Test
    fun `1 source file, 1 enum class`()  = test(
        sourceFilePaths = listOf(ONE_ENUM_CLASS),
        parsedElements = mapOf(SIZE to ENUM_CLASS)
    )

    @Test
    fun `1 source file, 2 same classes`() = test(
        sourceFilePaths = listOf(TWO_SAME_CLASSES),
        parsedElements = mapOf(CONTROLLER to INTERFACE, INTERACTOR to INTERFACE)
    )

    @Test
    fun `1 source file, 2 different classes`() = test(
        sourceFilePaths = listOf(TWO_DIFFERENT_CLASSES),
        parsedElements = mapOf(PERSON to OPEN_CLASS, PHONE to FINAL_CLASS)
    )

    @Test
    fun `2 source files, 1 open class`() = test(
        sourceFilePaths = listOf(EMPTY_FILE, ONE_OPEN_CLASS),
        parsedElements = mapOf(STORE to OPEN_CLASS)
    )

    @Test
    fun `2 source files, 2 different classes`() = test(
        sourceFilePaths = listOf(ONE_FINAL_CLASS, ONE_ENUM_CLASS),
        parsedElements = mapOf(LAST_MAN_ON_EARTH to FINAL_CLASS, SIZE to ENUM_CLASS)
    )

    @Test
    fun `2 source files, 3 different classes`() = test(
        sourceFilePaths = listOf(TWO_DIFFERENT_CLASSES, ONE_ABSTRACT_CLASS),
        parsedElements = mapOf(PERSON to OPEN_CLASS, PHONE to FINAL_CLASS, GAME to ABSTRACT_CLASS)
    )

    @Test
    fun `3 source files, 4 different classes`() = test(
        sourceFilePaths = listOf(ONE_ENUM_CLASS, TWO_DIFFERENT_CLASSES, ONE_OPEN_CLASS),
        parsedElements = mapOf(PERSON to OPEN_CLASS, PHONE to FINAL_CLASS, STORE to OPEN_CLASS, SIZE to ENUM_CLASS)
    )


    private fun test(
        sourceFilePaths: List<String>,
        parsedElements: Map<String, Element.Type> = emptyMap()
    ) {
        val parser = KotlinParser(SourceCodeReader())
        val result = parser.parse(sourceFilePaths)
        val actualModel = (result as Parser.Result.Success).model
        assertEquals(actual = actualModel.references.size, expected = 0)
        assertEquals(actual = actualModel.elements.size, expected = parsedElements.size)
        parsedElements.forEach { (name, type) ->
            val element = actualModel.elements.find { it.name == name }
            assertNotNull(element)
            assertEquals(actual = element?.type, expected = type)
        }
    }


    companion object {
        private const val DIRECTORY = "src/assets/kotlin_parser"
        const val EMPTY_FILE = "$DIRECTORY/Empty.kt"

        const val ONE_INTERFACE = "$DIRECTORY/Factory.kt"
        const val FACTORY = "Factory"

        const val ONE_FINAL_CLASS = "$DIRECTORY/Single.kt"
        const val LAST_MAN_ON_EARTH = "LastManOnEarth"

        const val ONE_OPEN_CLASS = "$DIRECTORY/Store.kt"
        const val STORE = "Store"

        const val ONE_ABSTRACT_CLASS = "$DIRECTORY/Game.kt"
        const val GAME = "Game"

        const val ONE_ENUM_CLASS = "$DIRECTORY/Size.kt"
        const val SIZE = "Size"

        const val TWO_SAME_CLASSES = "$DIRECTORY/Arch.kt"
        const val CONTROLLER = "Controller"
        const val INTERACTOR = "Interactor"

        const val TWO_DIFFERENT_CLASSES = "$DIRECTORY/Person.kt"
        const val PERSON = "Person"
        const val PHONE = "Phone"

        private const val JAVA_DIRECTORY = "src/assets/java_parser"
        const val JAVA_CLASS_A = "$JAVA_DIRECTORY/Car.java"
        const val JAVA_CLASS_B = "$JAVA_DIRECTORY/Animal.java"
    }
}
