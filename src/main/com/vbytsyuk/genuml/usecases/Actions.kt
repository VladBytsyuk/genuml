package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.Coordinate
import com.vbytsyuk.genuml.domain.Element
import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.domain.Reference


fun Model.addElement(type: Element.Type, coordinate: Coordinate): Element {
    val newElement = createElement(name = "", type = type, coordinate = coordinate)
    elements.add(newElement)
    return newElement
}

fun Model.addReference(
    source: Element, start: Coordinate,
    target: Element, end: Coordinate,
    type: Reference.Type
): Reference {
    val newReference = createReference(source, target, type, start, end)
    references.add(newReference)
    return newReference
}
