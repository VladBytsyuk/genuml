package com.vbytsyuk.genuml

import com.vbytsyuk.genuml.controllers.AppController
import com.vbytsyuk.genuml.controllers.ParseController
import com.vbytsyuk.genuml.parsers.KotlinParser
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import org.junit.Test
import kotlin.test.assertEquals

class AppControllerTest {
    @Test
    fun `create without parsers`() { AppController() }

    @Test
    fun `create with Kotlin parser`() {
        val parsers = listOf(KotlinParser(SourceCodeReader()))
        AppController(parsers)
    }


    @Test
    fun `parse some correct kt files`() {
        val parsers = listOf(KotlinParser(SourceCodeReader()))
        val appController = AppController(parsers)

        val directory = "src/assets/kotlin_parser"
        val paths = listOf("$directory/Game.kt", "$directory/Animals.kt")
        val parsingResult = appController.parseFiles(paths)

        require(parsingResult is ParseController.Result.Success)
        assertEquals(actual = parsingResult.model.elements.size, expected = 4)
        assertEquals(actual = parsingResult.model.references.size, expected = 0)
    }

    @Test
    fun `parse some correct files with unsupported extension`() {
        val parsers = listOf(KotlinParser(SourceCodeReader()))
        val appController = AppController(parsers)

        val directory = "src/assets/java_parser"
        val paths = listOf("$directory/Animal.java", "$directory/Car.java")
        val parsingResult = appController.parseFiles(paths)

        require(parsingResult is ParseController.Result.Success)
        assertEquals(actual = parsingResult.model.elements.size, expected = 0)
        assertEquals(actual = parsingResult.model.references.size, expected = 0)
    }
}