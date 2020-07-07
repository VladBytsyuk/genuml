package com.vbytsyuk.genuml.ui

import org.koin.core.KoinComponent
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier


inline fun <reified T> KoinComponent.injectKoin(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = getKoin().inject(qualifier, parameters)
