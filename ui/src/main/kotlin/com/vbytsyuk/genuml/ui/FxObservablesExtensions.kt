package com.vbytsyuk.genuml.ui

import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.collections.ObservableList


fun <T> List<T>.toObservableList(): ObservableList<T> =
    FXCollections.observableArrayList(this)

fun Observable.onChange(block: () -> Unit) =
    addListener { block() }
