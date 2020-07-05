package com.vbytsyuk.genuml.common

import com.vbytsyuk.genuml.ui.GenUmlApplication


/**
 * Entry point into GenUML application
 */
fun main(args: Array<String>) {
    initKoin()
    GenUmlApplication.launch(args)
}
