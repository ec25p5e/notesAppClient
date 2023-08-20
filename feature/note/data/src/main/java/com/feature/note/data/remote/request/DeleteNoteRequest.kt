package com.feature.note.data.remote.request

data class DeleteNoteRequest(
    val noteId: String,
    val userId: String
)