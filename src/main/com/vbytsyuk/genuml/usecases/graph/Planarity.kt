package com.vbytsyuk.genuml.usecases.graph

import com.vbytsyuk.genuml.domain.Model


/** Checks that Model can be placed on a canvas without references intersections */
val Model.isPlanar: Boolean
    get() = when {
        n <= 2 -> true
        definitelyNotPlanar -> false
        else -> true
    }


/** Euler criteria */
@Suppress("MagicNumber")
private val Model.canBePlanar: Boolean
    get() = n <= 2 || m < 3 * n - 6

private val Model.definitelyNotPlanar: Boolean get() = !canBePlanar
