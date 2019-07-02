package com.vbytsyuk.genuml

import com.vbytsyuk.genuml.domain.Coordinate
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Element.Type.FINAL_CLASS
import com.vbytsyuk.genuml.domain.Element.Type.OPEN_CLASS
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.domain.Reference
import com.vbytsyuk.genuml.domain.Reference.Type.ASSOCIATION
import com.vbytsyuk.genuml.usecases.DefaultStyle
import com.vbytsyuk.genuml.usecases.addElement
import com.vbytsyuk.genuml.usecases.addReference
import org.junit.Assert.assertEquals
import org.junit.Test


class UseCasesTest {

    @Test
    fun empty() {
        val expectedModel = Model()
        val actualModel = Model()
        assertEquals(expectedModel, actualModel)
    }

    @Test
    fun oneElement() {
        val expectedModel = Model(elements = mutableListOf(parent))
        val actualModel = Model()
            .apply { addElement(type = OPEN_CLASS, coordinate = Coordinate(x = 100, y = 50)) }
        assertEquals(expectedModel.elements.size, actualModel.elements.size)
        assertEquals(expectedModel.references.size, actualModel.references.size)
    }

    @Test
    fun twoElements() {
        val expectedModel = Model(elements = mutableListOf(parent, child))
        val actualModel = Model()
            .apply { addElement(type = OPEN_CLASS, coordinate = Coordinate(x = 100, y = 50)) }
            .apply { addElement(type = FINAL_CLASS, coordinate = Coordinate(x = 0, y = 500)) }
        assertEquals(expectedModel.elements.size, actualModel.elements.size)
        assertEquals(expectedModel.references.size, actualModel.references.size)
    }


    @Test
    fun twoElementsLinked() {
        val expectedModel = Model(
            elements = mutableListOf(parent, child),
            references = mutableListOf(childToParent)
        )
        val actualModel = Model()
            .apply { addElement(type = OPEN_CLASS, coordinate = Coordinate(x = 100, y = 50)) }
            .apply { addElement(type = FINAL_CLASS, coordinate = Coordinate(x = 0, y = 500)) }
            .apply {
                addReference(
                    type = ASSOCIATION,
                    start = Coordinate(x = 10, y = 500), source = child,
                    end = Coordinate(x = 110, y = 410), target = parent
                )
            }
        assertEquals(expectedModel.elements.size, actualModel.elements.size)
        assertEquals(expectedModel.references.size, actualModel.references.size)
    }


    companion object Constants {
        val parent = Element(
            name = "",
            id = "a",
            type = OPEN_CLASS,
            presentation = Element.Presentation(
                coordinate = Coordinate(x = 100, y = 50),
                style = DefaultStyle.Element
            )
        )
        val child = Element(
            name = "",
            id = "c",
            type = FINAL_CLASS,
            presentation = Element.Presentation(
                coordinate = Coordinate(x = 0, y = 500),
                style = DefaultStyle.Element
            )
        )

        val childToParent = Reference(
            id = "r",
            type = ASSOCIATION,
            source = child,
            target = parent,
            presentation = Reference.Presentation(
                start = Coordinate(x = 10, y = 500),
                end = Coordinate(x = 110, y = 410),
                style = DefaultStyle.Arrow
            )
        )

    }

}
