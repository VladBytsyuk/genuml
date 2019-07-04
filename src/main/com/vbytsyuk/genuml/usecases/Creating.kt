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

internal fun createArrowPresentation(
    start: Coordinate,
    end: Coordinate,
    style: Style.Arrow = DefaultStyle.Arrow
) =
    Reference.Presentation(start, end, style)
