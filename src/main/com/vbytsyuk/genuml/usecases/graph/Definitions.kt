package com.vbytsyuk.genuml.usecases.graph

import com.vbytsyuk.genuml.domain.Model


/** A set of vertices */
internal val Model.V get() = elements.toSet()

/** A set of edges */
internal val Model.E get() = references.toSet()


/** Number of vertices */
internal val Model.n: Int get() = V.size

/** Number of edges */
internal val Model.m: Int get() = E.size
