package com.vbytsyuk.genuml.controllers

import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.usecases.ModelMerger


abstract class Parser(private val sourceCodeReader: ISourceCodeReader) {

    sealed class Result {
        data class Success(val model: Model) : Result()
        data class Error(val message: String) : Result()
        internal object WrongExtension : Result()
    }


    abstract val extension: String

    fun parse(paths: List<String>): Result {
        val parsedSources = paths.toPathWithParsingResult()

        val error = parsedSources.filterByResult<Result.Error>()
        val success = parsedSources.filterByResult<Result.Success>()

        return when {
            error.isNotEmpty() -> Result.Error("Can't parse sources: ${error.mapToPaths()}")
            else -> success.mapToModels().merge()
        }
    }


    private fun List<String>.toPathWithParsingResult(): Map<String, Result> =
        this.map { path -> path to sourceCodeReader.readFile(path) }
            .map { (path, sourceCode) -> path to parse(sourceCode, path) }
            .toMap()

    private fun parse(sourceCode: List<String>, path: String): Result = when {
        path.endsWith(extension) -> parseFile(sourceCode)
        else -> Result.WrongExtension
    }

    protected abstract fun parseFile(sourceCodeLines: List<String>): Result
}


private inline fun <reified R : Parser.Result> Map<String, Parser.Result>.filterByResult(): Map<String, R> =
    mapNotNull { (path, result) -> if (result is R) path to result else null }.toMap()


private inline fun <reified R : Parser.Result> Map<String, R>.mapToPaths() =
    map { (path, _) -> path }

private fun Map<String, Parser.Result.Success>.mapToModels() =
    map { (_, result) -> result.model }


private fun List<Model>.merge(): Parser.Result = when (val modelMergerResult = ModelMerger.merge(this)) {
    is ModelMerger.Result.Success -> Parser.Result.Success(modelMergerResult.model)
    is ModelMerger.Result.Error -> Parser.Result.Error("Source files have name clashing. Incorrect source code.")
}
