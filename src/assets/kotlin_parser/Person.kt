package com.assets.examples.kotlin

open class Person {
    var name: Int
    var age: Int
    var phone: Phone?
}

data class Phone(val number: String)
