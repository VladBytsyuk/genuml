package com.assets.examples.kotlin

class Person {
    var name: Int
    var age: Int
    var phone: Phone?
}

data class Phone(val number: String)
