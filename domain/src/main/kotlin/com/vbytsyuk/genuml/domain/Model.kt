package com.vbytsyuk.genuml.domain


data class Model(
    val elements: MutableList<Element> = emptyMutableList(),
    val references: MutableList<Reference> = emptyMutableList()
)
