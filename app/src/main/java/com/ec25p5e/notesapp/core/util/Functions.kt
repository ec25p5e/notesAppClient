package com.ec25p5e.notesapp.core.util

fun <T> concatenate(vararg lists: List<T>): List<T> {
    val result: MutableList<T> = ArrayList()

    for (list in lists) {
        result.addAll(list)
    }

    return result
}