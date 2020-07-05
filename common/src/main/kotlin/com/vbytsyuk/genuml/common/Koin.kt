package com.vbytsyuk.genuml.common

import com.vbytsyuk.genuml.controllers.AppController
import com.vbytsyuk.genuml.controllers.ISourceCodeReader
import com.vbytsyuk.genuml.parsers.KotlinParser
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import com.vbytsyuk.genuml.controllers.ParseController
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() = startKoin {
    val controllerModule = module {
        single<ISourceCodeReader> { SourceCodeReader() }
        single<ParseController> { AppController(listOf(KotlinParser(get()))) }
    }
    modules(listOf(controllerModule))
}
