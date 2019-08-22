package com.vbytsyuk.genuml.controllers

import com.vbytsyuk.genuml.domain.Model


interface ParseController {
    fun parseFiles(paths: List<String>): Result

    sealed class Result {
        data class Success(val model: Model) : Result()
        data class Error(val errors: List<String>) : Result()
    }
}
