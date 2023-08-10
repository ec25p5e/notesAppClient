package com.ec25p5e.notesapp.feature_note.data.remote.response

import com.ec25p5e.notesapp.feature_note.domain.models.Category

data class CategoryResponse(
    val localId: String,
    val remoteId: String,
    val userId: String,
    val name: String,
    val color: Int,
    val timestamp: Long,
    val numNotesAssoc: Int,
)
