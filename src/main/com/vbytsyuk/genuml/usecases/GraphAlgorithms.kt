package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.Model

/**
 * Checks that Model can be placed on a canvas without references intersections
 */
val Model.isPlanar: Boolean
    get() = when {
        definitelyNotPlanar -> false
        else -> true
    }


/**
 * Euler criteria
 */
@Suppress("MagicNumber")
private val Model.canBePlanar: Boolean
    get() = references.size < 3 * elements.size - 6

private val Model.definitelyNotPlanar: Boolean
    get() = !canBePlanar
