package com.vbytsyuk.genuml.parsers

import com.vbytsyuk.genuml.controllers.ISourceCodeReader
import java.io.File


class SourceCodeReader : ISourceCodeReader {

    override fun readFile(pathToFile: String): List<String> =
        File(pathToFile).readLines()

}
