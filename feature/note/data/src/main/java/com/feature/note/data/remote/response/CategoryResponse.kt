package com.feature.note.data.remote.response

data class CategoryResponse(
    val localId: String,
    val remoteId: String,
    val userId: String,
    val name: String,
    val color: Int,
    val timestamp: Long,
    val numNotesAssoc: Int,
)
