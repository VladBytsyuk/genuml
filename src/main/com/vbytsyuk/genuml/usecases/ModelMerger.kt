package com.vbytsyuk.genuml.usecases

import com.vbytsyuk.genuml.domain.Model

object ModelMerger {

    sealed class Result {
        data class Success(val model: Model) : Result()
        object Error : Result()
    }

    fun merge(list: List<Model>): Result = when {
        list.isEmpty() -> Result.Success(model = Model())
        list.hasNameClashing -> Result.Error
        else -> Result.Success(
            model = list.reduce { acc, model ->
                acc.elements.addAll(model.elements)
                acc.references.addAll(model.references)
                return@reduce acc
            }
        )
    }
}

private val List<Model>.hasNameClashing: Boolean
    get() = any { model ->
        val elementsNames = model.elements.map { it.name }
        return@any elementsNames.distinct().size != elementsNames.size
    }
