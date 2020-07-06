package com.vbytsyuk.genuml.controllers

interface ISourceCodeReader {

    /**
     * Read a text file and returns a list of lines
     */
    fun readFile(pathToFile: String): String
}
