package com.assets.examples.kotlin

abstract class Game {

    abstract fun start()

    abstract val isStarted: Boolean

    abstract val winner: String
}
