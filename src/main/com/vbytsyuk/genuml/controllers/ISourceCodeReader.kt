package com.vbytsyuk.genuml.controllers

interface ISourceCodeReader {
    fun readFile(pathToFile: String): String
}
