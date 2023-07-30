package com.ec25p5e.notesapp.core.util

interface Paginator<T> {

    suspend fun loadNextItems()
}