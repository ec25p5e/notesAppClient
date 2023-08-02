package com.ec25p5e.notesapp.feature_note.data.remote.request

data class DeleteNoteRequest(
    val noteId: String,
    val userId: String
)