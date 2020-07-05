package com.vbytsyuk.genuml.common

import tornadofx.*
import com.vbytsyuk.genuml.ui.GenUmlApplication


/**
 * Entry point into GenUML application
 */
fun main(args: Array<String>) {
    initKoin()
    launch<GenUmlApplication>(args)
}
