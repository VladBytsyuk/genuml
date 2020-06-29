package com.vbytsyuk.genuml.ui

import main.initKoin
import tornadofx.*
import ui.GenUmlApplication


/**
 * Entry point into GenUML application
 */
fun main(args: Array<String>) {
    initKoin()
    launch<GenUmlApplication>(args)
}
