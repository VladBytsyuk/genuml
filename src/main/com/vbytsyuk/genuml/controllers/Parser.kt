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
        val parsedSources = paths
            .map { path -> path to sourceCodeReader.readFile(path) }
            .map { (path, sourceCode) -> path to parse(sourceCode, path) }
            .toMap()

        val error = parsedSources.filterByResult<Result.Error>()
        val success = parsedSources.filterByResult<Result.Success>()

        if (error.isNotEmpty()) {
            val errorPaths = error.mapToPaths()
            return Result.Error("Can't parse sources: $errorPaths")
        }

        return success.mapToResults().mapToModel().merge()
    }

    internal abstract fun parseFile(sourceCodeLines: List<String>): Result

    private fun parse(sourceCode: List<String>, path: String): Result = when {
        path.endsWith(extension) -> parseFile(sourceCode)
        else -> Result.WrongExtension
    }
}


private inline fun <reified R : Parser.Result> Map<String, Parser.Result>.filterByResult(): Map<String, R> =
    mapNotNull { (path, result) ->
        when (result) {
            is R -> path to result
            else -> null
        }
    }.toMap()

private inline fun <reified R : Parser.Result> Map<String, R>.mapToPaths() =
    map { (path, _) -> path }

private inline fun <reified R : Parser.Result> Map<String, R>.mapToResults() =
    map { (_, result) -> result }

private fun List<Parser.Result.Success>.mapToModel() = map { it.model }

private fun List<Model>.merge(): Parser.Result =
    when (val modelMergerResult = ModelMerger.merge(this)) {
        is ModelMerger.Result.Success -> Parser.Result.Success(modelMergerResult.model)
        ModelMerger.Result.Error -> Parser.Result.Error("Source files have name clashing. Incorrect source code.")
    }
