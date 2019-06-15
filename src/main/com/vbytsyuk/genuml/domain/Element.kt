package com.vbytsyuk.genuml.domain


data class Element(
    val id: Id,
    var type: Type,
    var name: String,
    val properties: MutableList<Property> = emptyMutableList(),
    val methods: MutableList<Method> = emptyMutableList(),
    var presentation: Presentation
) {
    enum class Type { INTERFACE, FINAL_CLASS, OPEN_CLASS, ABSTRACT_CLASS, ENUM_CLASS }

    data class Presentation(
        var coordinate: Coordinate,
        val style: Style.Element
    )
}

enum class VisibilityModifier { PRIVATE, PUBLIC, PROTECTED, INTERNAL }

data class Property(
    var visibilityModifier: VisibilityModifier,
    var mutability: Boolean,
    var name: String,
    var type: String,
    var nullability: Boolean
)

data class Method(
    var visibilityModifier: VisibilityModifier,
    var name: String,
    val arguments: MutableList<Argument> = emptyMutableList(),
    var returnType: String,
    var nullability: Boolean
)

data class Argument(val name: String, val type: String)

