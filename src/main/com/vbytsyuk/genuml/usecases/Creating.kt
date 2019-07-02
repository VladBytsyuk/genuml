package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.*
import java.util.*


internal fun generateId(): Id = UUID.randomUUID().toString()


/* =====================================================================================================================
 * Element
 * =====================================================================================================================
 */

internal fun createElement(
    name: String,
    type: Element.Type,
    coordinate: Coordinate
) = Element(
    id = generateId(),
    type = type,
    name = name,
    properties = emptyMutableList(),
    methods = emptyMutableList(),
    presentation = createElementPresentation(coordinate)
)

internal fun createInterface(name: String, coordinate: Coordinate) =
    createElement(name, Element.Type.INTERFACE, coordinate)

internal fun createOpenClass(name: String, coordinate: Coordinate) =
    createElement(name, Element.Type.OPEN_CLASS, coordinate)

internal fun createFinalClass(name: String, coordinate: Coordinate) =
    createElement(name, Element.Type.FINAL_CLASS, coordinate)

internal fun createAbstractClass(name: String, coordinate: Coordinate) =
    createElement(name, Element.Type.ABSTRACT_CLASS, coordinate)

internal fun createEnumClass(name: String, coordinate: Coordinate) =
    createElement(name, Element.Type.ENUM_CLASS, coordinate)


internal fun createElementPresentation(
    coordinate: Coordinate,
    style: Style.Element = DefaultStyle.Element
) =
    Element.Presentation(coordinate, style)


/* =====================================================================================================================
 * Reference
 * =====================================================================================================================
 */

internal fun createReference(
    source: Element, target: Element,
    type: Reference.Type,
    start: Coordinate, end: Coordinate
) = Reference(
    id = generateId(), type = type,
    source = source, target = target,
    presentation = createArrowPresentation(start, end)
)

internal fun createReferenceAssociation(
    source: Element,
    target: Element,
    start: Coordinate,
    end: Coordinate
) =
    createReference(source, target, Reference.Type.ASSOCIATION, start, end)

internal fun createReferenceAggregation(
    source: Element,
    target: Element,
    start: Coordinate,
    end: Coordinate
) =
    createReference(source, target, Reference.Type.AGGREGATION, start, end)

internal fun createReferenceComposition(
    source: Element,
    target: Element,
    start: Coordinate,
    end: Coordinate
) =
    createReference(source, target, Reference.Type.COMPOSITION, start, end)

internal fun createReferenceGeneralization(
    source: Element,
    target: Element,
    start: Coordinate,
    end: Coordinate
) =
    createReference(source, target, Reference.Type.GENERALIZATION, start, end)


internal fun createArrowPresentation(
    start: Coordinate,
    end: Coordinate,
    style: Style.Arrow = DefaultStyle.Arrow
) =
    Reference.Presentation(start, end, style)
