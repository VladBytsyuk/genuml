package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.Model

object ModelMerger {

    sealed class Result {
        data class Success(val model: Model) : Result()
        object Error : Result()
    }

    fun merge(list: List<Model>): Result {
        if (list.hasNameClashing) {
            return Result.Error
        }

        val mergedModel = list.reduce { acc, model ->
            acc.elements.addAll(model.elements)
            acc.references.addAll(model.references)
            return@reduce acc
        }
        return Result.Success(mergedModel)
    }
}

private val List<Model>.hasNameClashing: Boolean
    get() = any { model ->
        val elementsNames = model.elements.map { it.name }
        return@any elementsNames.distinct().size != elementsNames.size
    }
