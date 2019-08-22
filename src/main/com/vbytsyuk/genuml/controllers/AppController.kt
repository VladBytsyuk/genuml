package com.vbytsyuk.genuml.controllers

import com.vbytsyuk.genuml.domain.Model
import com.vbytsyuk.genuml.usecases.ModelMerger
import java.lang.IllegalArgumentException


class AppController(
    private val parsers: List<Parser> = emptyList()
) : ParseController {

    override fun parseFiles(paths: List<String>): ParseController.Result {
        val (models, errors) = paths.toModelsAndErrorsLists()
        return when {
            errors.isNotEmpty() -> ParseController.Result.Error(errors)
            else -> models.toParseControllerResult(paths)
        }

    }

    private fun List<String>.toModelsAndErrorsLists(): Pair<List<Model>, List<String>> {
        val models: MutableList<Model> = ArrayList()
        val errors: MutableList<String> = ArrayList()

        parsers.forEach {
            when (val parserResult = it.parse(this)) {
                is Parser.Result.Success -> models.add(parserResult.model)
                is Parser.Result.Error -> errors.add(parserResult.message)
                Parser.Result.WrongExtension -> return@forEach
            }
        }

        return models to errors
    }

    private fun List<Model>.toParseControllerResult(paths: List<String>) =
        when (val mergeResult = ModelMerger.merge(this)) {
            is ModelMerger.Result.Success -> ParseController.Result.Success(mergeResult.model)
            ModelMerger.Result.Error -> throw IllegalArgumentException("Can't merge $paths to model")
        }
}
