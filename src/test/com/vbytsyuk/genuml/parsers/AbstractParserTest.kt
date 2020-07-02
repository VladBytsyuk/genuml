package com.vbytsyuk.genuml.parsers

import com.vbytsyuk.genuml.controllers.Parser
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

abstract class AbstractParserTest(private val parser: Parser) {

    protected fun test(sourceFilePaths: List<String>, parsedElements: Map<String, Element.Type>) {
        val model = testModelConstruction(sourceFilePaths)
        testModelContent(model, parsedElements)
    }

    private fun testModelConstruction(sourceFilePaths: List<String>): Model {
        val result = parser.parse(sourceFilePaths)

        assertTrue { result is Parser.Result.Success }

        return (result as Parser.Result.Success).model
    }
}

private fun testModelContent(model: Model, parsedElements: Map<String, Element.Type>) {
    val referencesCount = model.references.size
    val elementsCount = model.elements.size
    val expectedElementsCount = parsedElements.size

    assertEquals(actual = referencesCount, expected = 0)
    assertEquals(actual = elementsCount, expected = expectedElementsCount)
    assertElements(model, parsedElements)
}

private fun assertElements(
    model: Model,
    parsedElements: Map<String, Element.Type>
) = parsedElements.forEach { (expectedName, expectedType) ->
    val actualElement = model.elements.find { it.name == expectedName }

    assertNotNull(actualElement)
    assertEquals(actual = actualElement?.type, expected = expectedType)
}
