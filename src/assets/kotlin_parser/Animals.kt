package com.assets.examples.;

open class Pet {
    val name: String
    val weight: Double

    fun eat() {
        /* empty implementation */
    }
}

class Dog : Pet() {
    fun bark() {
        /* empty implementation */
    }
}

class Cat : Pet() {
    fun meow() {
        /* empty implementation */
    }
}
