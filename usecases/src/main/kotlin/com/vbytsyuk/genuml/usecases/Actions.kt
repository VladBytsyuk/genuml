package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.*


fun Model.addElement(
    type: Element.Type,
    coordinate: Coordinate,
    name: String = "",
    properties: MutableList<Property> = emptyMutableList(),
    methods: MutableList<Method> = emptyMutableList()
): Element {
    val newElement = createElement(
        name = name,
        type = type,
        coordinate = coordinate,
        properties = properties,
        methods = methods
    )
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

fun Element.addProperty(
    visibilityModifier: VisibilityModifier,
    mutability: Boolean,
    name: String,
    type: String,
    nullability: Boolean
): Property {
    val property = Property(
        visibilityModifier, mutability,
        name, type, nullability
    )
    properties.add(property)
    return property
}

fun Element.addMethod(
    visibilityModifier: VisibilityModifier,
    name: String,
    arguments: MutableList<Argument>,
    returnType: String,
    nullability: Boolean
): Method {
    val method = Method(
        visibilityModifier, name,
        arguments, returnType, nullability
    )
    methods.add(method)
    return method
}
