package com.vbytsyuk.genuml.domain


data class Reference(
    val id: Id,
    var type: Type,
    var source: Element,
    var target: Element,
    var presentation: Presentation
) {
    enum class Type { ASSOCIATION, AGGREGATION, COMPOSITION, GENERALIZATION }

    data class Presentation(
        var start: Coordinate,
        var end: Coordinate,
        val style: Style.Arrow
    )
}
