package com.vbytsyuk.genuml.parsers

import com.vbytsyuk.genuml.domain.Element
import org.junit.Test

class JavaParserTest : ParserTest(JavaParser(SourceCodeReader())) {

    @Test
    fun `car test`() = test(
        sourceFilePaths = listOf(CAR),
        parsedElements = mapOf("Car" to Element.Type.OPEN_CLASS)
    )

    companion object {
        private const val DIRECTORY = "../assets/java_parser"

        const val CAR = "$DIRECTORY/Car.java"
    }
}
