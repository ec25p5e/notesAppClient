package com.ec25p5e.notesapp.feature_note.data.remote.request

data class CreateCategoryRequest(
    val userId: String,
    val localId: String,
    val remoteId: String,
    val name: String,
    val timestamp: Long,
    val color: Int,
    val numNotesAssoc: Int,
)
