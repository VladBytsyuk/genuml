package com.vbytsyuk.genuml.common

import com.vbytsyuk.genuml.controllers.AppController
import com.vbytsyuk.genuml.controllers.ISourceCodeReader
import com.vbytsyuk.genuml.controllers.ParseController
import com.vbytsyuk.genuml.parsers.JavaParser
import com.vbytsyuk.genuml.parsers.KotlinParser
import com.vbytsyuk.genuml.parsers.SourceCodeReader
import org.koin.core.context.startKoin
import org.koin.dsl.module


fun initKoin() = startKoin {
    val controllerModule = module {
        single<ISourceCodeReader> { SourceCodeReader() }
        single<ParseController> {
            AppController(
                KotlinParser(sourceCodeReader = get()),
                JavaParser(sourceCodeReader = get())
            )
        }
    }
    modules(listOf(controllerModule))
}
