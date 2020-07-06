package com.vbytsyuk.genuml.domain


typealias Id = String
typealias HexCode = String
typealias Px = Int

/**
 * Wrapper around [mutableListOf] to receive only empty MutableList instance
 */
fun <T> emptyMutableList(): MutableList<T> = mutableListOf()
